# Create: Food Filling
[![CurseForge](https://img.shields.io/curseforge/dt/1478814?logo=curseforge&label=CurseForge)](https://www.curseforge.com/minecraft/mc-mods/create-food-filling)
[![Modrinth](https://img.shields.io/modrinth/dt/R1u731qJ?logo=modrinth&label=Modrinth)](https://modrinth.com/mod/create-food-filling)
[![MCMOD](https://img.shields.io/badge/MC百科-MCMOD-lime)](https://www.mcmod.cn/class/25807.html)
## Overview
A mod that allows you to fill food items with potions by spout.  
Eating the food will give you the potion effects.  
![alt text](<image/food_filling.png>)
![alt text](<image/potion_tooltips.png>)
This mod is a `Create` addon, so it requires `Create 6.0.4+` to work.

You can enable or disable these features in the config file, or adjust the amount consumed per filling.

## Environment

Installing this mod on the server is sufficient to enable all features.  

By default, the mod must be installed on the client to display potion effect tooltips.  
However, you can configure the display mode in the config file to allow tooltips to show even if the mod is **only installed on the server**.

## Compatibility
- 1.21.1 
  - Theoretically, it supports all fluids that have a `potion_contents` component and provide status effects via `MobEffectInstance`, as well as all foods with the `c:foods` tag or `food` component.
- 1.20.1
  - Supports all fluids that can be read effects by `PotionUtils.getAllEffects()` as well as all food that `isEatble`.

You can also use the item tag `create_food_filling:allow_filled` to allow items be filled, or use the tag `create_food_filling:disallow_filled` to disallow.

### Mod Support
- **[Tooltips Reforged](https://github.com/CodeOfArdonia/TooltipsReforged)**: When installed on the client, potion tooltips will be rendered in its style (icons) instead of vanilla text.
