#!/bin/bash
# ============================================================
# vps_monitor.sh
# Archivo    : tools/vps_monitor.sh
# Proyecto   : Sistema Web ERP en la nube - OMCGC
# Proposito  : Dashboard de monitoreo en tiempo real del VPS.
#              Muestra estado de servicios, recursos del sistema,
#              logs recientes y reinicia automaticamente el
#              backend Java si se detecta una caida.
# Autor      : Ing. Gabriel Amilcar Cruz Canto
# Version    : 1.0
# Fecha      : 19 de Febrero de 2026
# Ejecucion  : Se despliega automaticamente via VPS_CONNECT.bat
# ============================================================

# --- CONFIGURACION ---
REFRESH=30                           # Segundos entre cada actualizacion
SERVICE_NAME="omcgc-erp.service"     # Nombre del servicio systemd
LOG_FILE="/root/erp_log.txt"         # Archivo de log del backend
LOG_LINES=5                          # Lineas de log a mostrar
API_DOMAIN="api-vps.graxsoft.com"    # Dominio del backend

# --- COLORES ANSI ---
R='\033[0;31m'    # Rojo
G='\033[0;32m'    # Verde
Y='\033[1;33m'    # Amarillo
C='\033[0;36m'    # Cyan
W='\033[1;37m'    # Blanco brillante
D='\033[0;90m'    # Gris
N='\033[0m'       # Reset
B='\033[1m'       # Bold

# --- FUNCIONES AUXILIARES ---

# Indicador visual de estado (punto de color)
status_dot() {
    if [ "$1" = "active" ] || [ "$1" = "running" ]; then
        echo -e "${G}● ACTIVO${N}"
    elif [ "$1" = "warning" ]; then
        echo -e "${Y}● ALERTA${N}"
    else
        echo -e "${R}● CAIDO${N}"
    fi
}

# Barra de progreso visual (porcentaje)
progress_bar() {
    local pct=$1
    local width=20
    local filled=$((pct * width / 100))
    local empty=$((width - filled))
    local color=$G
    if [ "$pct" -gt 80 ]; then color=$R;
    elif [ "$pct" -gt 60 ]; then color=$Y; fi
    printf "${color}["
    printf '%0.s█' $(seq 1 $filled 2>/dev/null) 
    printf '%0.s░' $(seq 1 $empty 2>/dev/null)
    printf "] %3d%%${N}" "$pct"
}

# Contador de reinicios automaticos en esta sesion
AUTO_RESTARTS=0

# --- BUCLE PRINCIPAL ---
while true; do
    clear

    # =====================
    # RECOLECCION DE DATOS
    # =====================
    TIMESTAMP=$(date '+%Y-%m-%d %H:%M:%S')
    UPTIME_SYS=$(uptime -p 2>/dev/null | sed 's/up //' || uptime | awk -F'( |,)' '{print $5}')

    # Servicios
    JAVA_STATUS=$(systemctl is-active $SERVICE_NAME 2>/dev/null || echo "inactive")
    NGINX_STATUS=$(systemctl is-active nginx 2>/dev/null || echo "inactive")
    MARIA_STATUS=$(systemctl is-active mariadb 2>/dev/null || echo "inactive")

    # Java PID y memoria
    JAVA_PID=$(pgrep -f "omcgc-erp" 2>/dev/null | head -1)
    if [ -n "$JAVA_PID" ]; then
        JAVA_MEM=$(ps -p $JAVA_PID -o rss= 2>/dev/null | awk '{printf "%.0f", $1/1024}')
        JAVA_CPU=$(ps -p $JAVA_PID -o %cpu= 2>/dev/null | awk '{printf "%.1f", $1}')
        JAVA_UPTIME=$(ps -p $JAVA_PID -o etime= 2>/dev/null | xargs)
    else
        JAVA_MEM="-"
        JAVA_CPU="-"
        JAVA_UPTIME="-"
    fi

    # Verificar puerto 9090
    PORT_9090=$(ss -tlnp 2>/dev/null | grep -c ":9090 " || echo "0")

    # Sistema
    CPU_PCT=$(top -bn1 2>/dev/null | grep "Cpu(s)" | awk '{printf "%.0f", 100 - $8}' || echo "0")
    RAM_TOTAL=$(free -m 2>/dev/null | awk '/Mem:/{print $2}' || echo "0")
    RAM_USED=$(free -m 2>/dev/null | awk '/Mem:/{print $3}' || echo "0")
    RAM_PCT=$((RAM_USED * 100 / (RAM_TOTAL + 1)))
    DISK_TOTAL=$(df -h / 2>/dev/null | awk 'NR==2{print $2}' || echo "-")
    DISK_USED=$(df -h / 2>/dev/null | awk 'NR==2{print $3}' || echo "-")
    DISK_PCT=$(df / 2>/dev/null | awk 'NR==2{gsub("%",""); print $5}' || echo "0")

    # Conexiones activas al backend
    CONNECTIONS=$(ss -tn 2>/dev/null | grep -c ":9090 " || echo "0")

    # Logs recientes
    if [ -f "$LOG_FILE" ]; then
        RECENT_LOGS=$(tail -n $LOG_LINES "$LOG_FILE" 2>/dev/null)
    else
        RECENT_LOGS="(Sin archivo de log)"
    fi

    # =====================
    # AUTO-RESTART
    # =====================
    RESTART_MSG=""
    if [ "$JAVA_STATUS" != "active" ]; then
        AUTO_RESTARTS=$((AUTO_RESTARTS + 1))
        RESTART_MSG="${Y}  ⚠  Backend CAIDO detectado. Reiniciando... (intento #$AUTO_RESTARTS)${N}"
        systemctl restart $SERVICE_NAME 2>/dev/null
        sleep 3
        JAVA_STATUS=$(systemctl is-active $SERVICE_NAME 2>/dev/null || echo "inactive")
        if [ "$JAVA_STATUS" = "active" ]; then
            RESTART_MSG="${G}  ✔  Backend reiniciado exitosamente (intento #$AUTO_RESTARTS)${N}"
            JAVA_PID=$(pgrep -f "omcgc-erp" 2>/dev/null | head -1)
        else
            RESTART_MSG="${R}  ✘  Fallo el reinicio automatico (intento #$AUTO_RESTARTS)${N}"
        fi
    fi

    # =====================
    # RENDERIZADO DASHBOARD
    # =====================
    echo -e "${C}╔══════════════════════════════════════════════════════════════╗${N}"
    echo -e "${C}║${W}${B}  VPS MONITOR — ERP WALOOK (OMCGC)                          ${C}║${N}"
    echo -e "${C}║${D}  $TIMESTAMP  |  Refresh: ${REFRESH}s  |  Ctrl+C: salir       ${C}║${N}"
    echo -e "${C}╠══════════════════════════════════════════════════════════════╣${N}"
    echo -e "${C}║${N}"
    echo -e "${C}║${W}  📡 SERVICIOS${N}"
    echo -e "${C}║${N}  ├─ Backend Java (omcgc-erp) ... $(status_dot $JAVA_STATUS)"
    echo -e "${C}║${N}  ├─ Nginx (SSL Proxy)        ... $(status_dot $NGINX_STATUS)"
    echo -e "${C}║${N}  ├─ MariaDB                  ... $(status_dot $MARIA_STATUS)"
    echo -e "${C}║${N}  └─ Puerto 9090              ... $([ "$PORT_9090" -gt 0 ] && echo -e "${G}● ESCUCHANDO${N}" || echo -e "${R}● CERRADO${N}")"
    echo -e "${C}║${N}"
    echo -e "${C}╠══════════════════════════════════════════════════════════════╣${N}"
    echo -e "${C}║${N}"
    echo -e "${C}║${W}  ☕ BACKEND JAVA${N}"
    echo -e "${C}║${N}  ├─ PID ............. ${W}${JAVA_PID:-N/A}${N}"
    echo -e "${C}║${N}  ├─ Memoria JVM .... ${W}${JAVA_MEM} MB${N}"
    echo -e "${C}║${N}  ├─ CPU ............. ${W}${JAVA_CPU}%${N}"
    echo -e "${C}║${N}  ├─ Uptime ......... ${W}${JAVA_UPTIME}${N}"
    echo -e "${C}║${N}  ├─ Conexiones ..... ${W}${CONNECTIONS}${N}"
    echo -e "${C}║${N}  └─ Dominio ........ ${W}${API_DOMAIN}${N}"
    echo -e "${C}║${N}"
    echo -e "${C}╠══════════════════════════════════════════════════════════════╣${N}"
    echo -e "${C}║${N}"
    echo -e "${C}║${W}  💾 RECURSOS DEL SISTEMA${N}"
    echo -e "${C}║${N}  ├─ CPU ........ $(progress_bar $CPU_PCT)"
    echo -e "${C}║${N}  ├─ RAM ........ $(progress_bar $RAM_PCT)  ${D}(${RAM_USED}/${RAM_TOTAL} MB)${N}"
    echo -e "${C}║${N}  ├─ Disco ...... $(progress_bar $DISK_PCT)  ${D}(${DISK_USED}/${DISK_TOTAL})${N}"
    echo -e "${C}║${N}  └─ Uptime ..... ${W}${UPTIME_SYS}${N}"
    echo -e "${C}║${N}"
    echo -e "${C}╠══════════════════════════════════════════════════════════════╣${N}"
    echo -e "${C}║${N}"
    echo -e "${C}║${W}  📋 ULTIMOS LOGS${N}"
    if [ -n "$RECENT_LOGS" ]; then
        echo "$RECENT_LOGS" | while IFS= read -r line; do
            # Colorear segun tipo de log
            if echo "$line" | grep -qi "error\|exception\|fatal"; then
                echo -e "${C}║${R}  $line${N}"
            elif echo "$line" | grep -qi "warn"; then
                echo -e "${C}║${Y}  $line${N}"
            else
                echo -e "${C}║${D}  $line${N}"
            fi
        done
    else
        echo -e "${C}║${D}  (Sin logs disponibles)${N}"
    fi
    echo -e "${C}║${N}"
    echo -e "${C}╠══════════════════════════════════════════════════════════════╣${N}"

    # Mensaje de auto-restart si hubo
    if [ -n "$RESTART_MSG" ]; then
        echo -e "${C}║${N}"
        echo -e "${C}║$RESTART_MSG"
        echo -e "${C}║${N}"
        echo -e "${C}╠══════════════════════════════════════════════════════════════╣${N}"
    fi

    echo -e "${C}║${N}  🔄 Auto-restart: ${G}ACTIVO${N}  |  Reinicios esta sesion: ${W}${AUTO_RESTARTS}${N}"
    echo -e "${C}║${N}  ⏳ Proxima verificacion en ${W}${REFRESH}${N} segundos..."
    echo -e "${C}╚══════════════════════════════════════════════════════════════╝${N}"

    # Esperar antes de la siguiente iteracion
    sleep $REFRESH
done
