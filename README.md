# Create: Food Filling
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
Theoretically, it supports all fluids that have a `potion_contents` component and provide status effects via `MobEffectInstance`, as well as all foods with the `c:foods` tag or `food` component.