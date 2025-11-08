#!/bin/bash
while true; do
    battery_level=$(cat /sys/class/power_supply/BAT0/capacity)
    battery_status=$(cat /sys/class/power_supply/BAT0/status)
    
    if [ "$battery_level" -le 10 ] && [ "$battery_status" = "Discharging" ]; then
        systemctl poweroff
    fi
    sleep 60
done
