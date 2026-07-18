# Changelog

## 1.5.1 26-7-18
- Fixed a mistake in 1.20.1 while filling glass bottles with potion fluids, which caused the glass bottles being filled as `potioned food` instead of the `potion` item.

## 1.5.0 26-7-2
- Added JEI compatibility for food filling.
    - Filled foods are now shown in a dedicated JEI category with Create Spout-style visuals.
    - Each fillable food has its own recipe page, with potion fluids rotating in sync with the displayed filled-food output.
- Changed the default value of `isAnimalsFoodFilled` to `false` (in `create_food_filling-server.toml`).
    - Animal-food-only items are no longer fillable by default unless this option is enabled.
    - This is mainly because enabling it by default makes the food filling recipes in JEI less visually clean.

## 1.0.2 26-4-9
- Fixed the mistake where the spout keeps filling infinitely without cost any fluid.

## 1.0.1 26-4-9
- Fixed the wrong path of datapack. 

## 1.0.0 26-4-8
init
