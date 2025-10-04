# /etc/skel/.bashrc
#
# This file is sourced by all *interactive* bash shells on startup,
# including some apparently interactive shells such as scp and rcp
# that can't tolerate any output.  So make sure this doesn't display
# anything or bad things will happen !


# Test for an interactive shell.  There is no need to set anything
# past this point for scp and rcp, and it's important to refrain from
# outputting anything in those cases.
if [[ $- != *i* ]] ; then
	# Shell is non-interactive.  Be done now!
	return
fi


# Put your fun stuff here.

# Important environment variables before anything else
export HISTFILE="${XDG_STATE_HOME}"/bash/history
export XDG_CURRENT_DESKTOP=Sway
export GTK_THEME='adw-gtk3-dark'
alias wget="wget --hsts-file=$XDG_DATA_HOME/wget-hsts"

# Important for environment (from https://github.com/swaywm/sway/wiki/Running-programs-natively-under-wayland)
export QT_QPA_PLATFORM=wayland-egl
export QT_WAYLAND_FORCE_DPI=physical
export QT_WAYLAND_DISABLE_WINDOWDECORATION=1

export ECORE_EVAS_ENGINE=wayland_egl
export ELM_ENGINE=wayland_egl
export SDL_VIDEODRIVER=wayland

export _JAVA_AWT_WM_NONREPARENTING=1

# Fish Support
[ -x /bin/fish ] && SHELL=/bin/fish exec fish
