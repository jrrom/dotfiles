{ inputs, outputs, lib, config, pkgs, pkgs-unstable, information, ... }:

{
  targets.genericLinux.enable = true;
  # You can import other home-manager modules here
  imports = [
  ];

  nixpkgs = {
    config = {
      allowUnfree = true;
    };
  };

  home = {
    username = information.hostName;
    homeDirectory = "/home/${information.hostName}";
  };

  home.packages = with pkgs-unstable; [
    # Fonts for Daily Use
    libre-baskerville
    iosevka-comfy.comfy-wide
    aporetic
    ubuntu-sans
    etBook
    noto-fonts-color-emoji
    maple-mono.truetype-autohint
    inter

    # Programs
    rsync

    # Languages
    smlnj
    
    # LSPs
    nixd
    basedpyright
  ];
  
# =================================
  
  # Enable home-manager and git
  programs.home-manager.enable = true;

  home.stateVersion = "24.11";
}
