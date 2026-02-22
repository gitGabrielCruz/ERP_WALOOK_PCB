#!/bin/bash
LOG=/tmp/watchdog.log
echo "[$(date)] Watchdog iniciado." > $LOG
while true; do
  if ! systemctl is-active --quiet omcgc-erp.service 2>/dev/null; then
    echo "[$(date)] Backend CAIDO - reiniciando..." >> $LOG
    systemctl restart omcgc-erp.service 2>/dev/null
    sleep 5
    if systemctl is-active --quiet omcgc-erp.service; then
      echo "[$(date)] Backend reiniciado OK." >> $LOG
    else
      echo "[$(date)] ERROR: reinicio fallido." >> $LOG
    fi
  fi
  sleep 60
done
