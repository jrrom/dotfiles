#!/bin/bash


# Managed by HSETC

check() {
    require_binaries /usr/bin/setvtrgb || return 1
}

depends() {
    return 0
}

install() {
    inst_binary /usr/bin/setvtrgb 
    inst /home/jrrom/dotfiles/root/gruvbox
    inst_hook pre-udev 20 "$moddir/setcolors.sh"
}
