# Changelog

## 1.5.0 26-7-2
- Added JEI compatibility for food filling.
  - Filled foods are now shown in a dedicated JEI category with Create Spout-style visuals.
  - Each fillable food has its own recipe page, with potion fluids rotating in sync with the displayed filled-food output.
- Changed the default value of `isAnimalsFoodFilled` to `false` (in `create_food_filling-server.toml`).
  - Animal-food-only items are no longer fillable by default unless this option is enabled.
  - This is mainly because enabling it by default makes the food filling recipes in JEI less visually clean.

## 1.21.1-1.4.0 26-6-1
- Added potion effect application for animals when fed or consuming potioned foods:
  - Foxes, pandas, and villagers now receive potion effects when eating potioned foods.
  - Generic animals receive potion effects when players feed them with potioned foods.
- Expanded potion filling support to items consumable by animals (e.g., bamboo) via `create_food_filling:animals_food` tag, regardless of being food categories for players.
  - Configure via `isAnimalsFoodFilled` option (default enabled); when disabled, only `create_food_filling:allow_filled` and vanilla food items can be filled (in priority order: disallow_filled > vanilla foods > animals_food > allow_filled).
- Introduced granular configuration options for all new animal-related features (all configurable in server config):
  - `enableFeedAnimalFoodEffects`: Toggle potion effects for animals when fed by players.
  - `enableFoxFoodEffects`: Toggle potion effects for foxes when eating.
  - `enablePandaFoodEffects`: Toggle potion effects for pandas when eating.
  - `enableVillagerFoodEffects`: Toggle potion effects for villagers during breeding.
  - `isPreFilterEvent`: Control pre-event validation checks before posting eating-related events.

## 1.3.1 26-4-9
- Fixed the wrong path of datapack.

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
