# Changelog

## 1.3.0 26-4-8
- Added two tags: `allow_filled` and `disallow_filled` for potion tooltip providers. These tags can be used to specify whether the provider should be applied to filled potion items (i.e., potions that have been filled with a potion effect using a brewing stand) or not.
  - Implemented compatibility with `Create: Some Assembly Required` by adding the `allow_filled` tag to `somesssemblyrequired:sandwich`. 

## 1.2.0 26-3-11
- Implemented compatibility with `Tooltips Reforged`.
    - When both this mod and `Tooltips Reforged` are installed on the client, tooltips will be displayed in the `Tooltips Reforged` style by default (i.e., when the `isPotionTooltip` configuration is `CLIENT` mode).

## 1.1.1 26-3-10
- No major updates, just simply removed some forgotten files to make the package more clean and tidy.

## 1.1.0 26-3-9
- Implemented potion tooltips for potioned food items.

## 1.0.0 26-3-4
- init