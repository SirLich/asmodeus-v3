# lichrpg
LichRpg is a [Spigot](https://www.spigotmc.org/resources/armor-stand-tools.2237/) plugin developed for the most recent 
version of [Minecraft](https://minecraft.net/en-us/). The plugin is primarily for use on the Minecraft multiplayer server, 
[The Dread Pits](https://discord.gg/XjV87YN), however branches of the repo are also being developed for 
[AelusenMC](https://discord.gg/SkhZPQT), and for public use. 

# Features:
lichrpg is a feature-packed plugin with lots to offer. However, to keep the plugin from developing a case of bloat, 
many per-server features (part of the original Dread Pits build) have been removed from the public release. 
To make full use of the plugin, server owners are encouraged to make changes directly into the source code of the plugin, 
and compile their own version. 

### Basic feature list:
 
 - Zones: (a bit like worldguard)
    - Easily created, viewed, and edited from in-game
    - Powerful tagging system for custom game-rules: (some examples)
        - no_fall_damage  → disable fall damage 
        - respawn → player who die in this zone will respawn with custom spawn point
        - <*potion name*>_<*power level*> → apply potion effects to players in this zone 
    - Support for lichrpg Arenas 
 - Arenas: (Pvp zones)
    - Arenas are an extension of zones, which makes them easy to implement in your server. 
    - Custom entry-method allows Arenas to be planted directly into survival worlds
    - OP arenas:
        - Keep your gear
        - Win custom upgrades on Kill
    - KitPvp:
        - Entering replaces your inventory with a custom gear loadout
        - Your original items are saved until you leave the arena 
 - Items:
    - Easy to create and edit in game
    - Support for all sorts of on-click actions via a strong tagging system
    - API support for using lichrpg items outside of the plugin
 - Gates:
    - Powerful gate-system driven by zones + colored glass. 
    - Easily contain your player-base, and allow access based on permission-nodes.
 - NPCs:
    - Full-support quest system
    - Easy to edit YML configuration 
    - Support for NPC to NPC communication (allows complicated quest-lines)
    - Actions: 
        - Take item
        - Teleport player
        - Give item
        - Give permission node
    - Based on logic:
        - Has item
        - Has permission node
        
# Documentation
Further documentation can be found in the *documentation* folder on the top level of the repo. The folder contains a slew 
examples, as well as some specific documentation that does not really belong here. 

### Permission Nodes:

Remember that you can use a .* to represent all permission nodes past a certain point. For example, lichrpg.* will give access 
to all lichrpg permissions.  

- State:
    - lichrpg.state.get.self 
    - lichrpg.state.get.other 
    - lichrpg.state.set.self 
    - lichrpg.state.set.other
- Items:
    - lichrpg.item.list
    - lichrpg.item.get.self
    - lichrpg.item.get.other
    - lichrpg.item.create
    - lichrpg.item.delete
- Zones:
    - lichrpg.zone.create
    - lichrpg.zone.delete
    - lichrpg.zone.list
    - lichrpg.zone.view
    - lichrpg.zone.inside
    
### Zone tags:
- respawn_<_level_>: players who die in this zone will respawn at a specified location. The level specifies which zone "wins"
in the case of over-laping zones.
- <_potion name_>_<_level_>: applies potion effects to players in the zone 
    - Example: REGENERATION_4
- no_fall_damage: cancels fall damage in this zone. Useful for parkour and the like 
- no_hunger: cancels hunger in this zone 
- no_damage: cancels all damage  
    
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
