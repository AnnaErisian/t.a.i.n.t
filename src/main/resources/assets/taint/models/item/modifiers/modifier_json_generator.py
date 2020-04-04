#!/usr/bin/env python3
for modifierSpec in [
    ('bane_of_arthopods', 'bane_spider'), # Why TConstruct…
    ('beheading', 'beheading'),
    ('diamond', 'diamond'),
    ('emerald', 'emerald'),
    ('glowing', 'glowing'),
    ('haste', 'haste'),
    ('knockback', 'knockback'),
    ('luck', 'luck'),
    ('mending_moss', 'mending_moss'),
    ('necrotic', 'necrotic'),
    ('reinforced', 'reinforced'),
    ('sharpness', 'sharpness'),
    ('shulking', 'shulking'),
    ('silktouch', 'silk'),
    ('smite', 'smite'),
    ('soulbound', 'soulbound'),
    ('webbed', 'web'),
    ]:
    with open(modifierSpec[0]+'.json', 'w', encoding='utf-8') as file:
        file.write('''{
\t"textures": {
\t\t"dagger": "taint:items/dagger/mod_%(shortName)s",
\t\t"waraxe": "taint:items/waraxe/mod_%(shortName)s",
\t\t"spear": "taint:items/spear/mod_%(shortName)s",
\t\t"glaive": "taint:items/glaive/mod_%(shortName)s",
\t\t"shield": "taint:items/shield/mod_%(shortName)s",
\t\t"buckler": "taint:items/buckler/mod_%(shortName)s"
\t}
}''' % { 'shortName': modifierSpec[1] })
