#!/usr/bin/env python3
for metal in [
    "ardorum",
    "termium",
    "adipatum",
    "caersin",
    "neulite",
    "atercaeum",
    "inurose",
    "oscurum",
    "cibarite"
    ]:
    with open(metal+'_boots.json', 'w', encoding='utf-8') as file:
        file.write('''{
  "result": {
    "item": "taint:%(metal)s_boots"
  },
  "pattern": [
    "A A",
    "A A"
  ],
  "type": "forge:ore_shaped",
  "key": {
    "A": {
      "item": "taint:ingot_%(metal)s"
    }
  }
}''' % { 'metal': metal })
    with open(metal+'_leggings.json', 'w', encoding='utf-8') as file:
        file.write('''{
  "result": {
    "item": "taint:%(metal)s_leggings"
  },
  "pattern": [
    "AAA",
    "A A",
    "A A"
  ],
  "type": "forge:ore_shaped",
  "key": {
    "A": {
      "item": "taint:ingot_%(metal)s"
    }
  }
}''' % { 'metal': metal })
    with open(metal+'_chestplate.json', 'w', encoding='utf-8') as file:
        file.write('''{
  "result": {
    "item": "taint:%(metal)s_chestplate"
  },
  "pattern": [
    "A A",
    "AAA",
    "AAA"
  ],
  "type": "forge:ore_shaped",
  "key": {
    "A": {
      "item": "taint:ingot_%(metal)s"
    }
  }
}''' % { 'metal': metal })
    with open(metal+'_helmet.json', 'w', encoding='utf-8') as file:
        file.write('''{
  "result": {
    "item": "taint:%(metal)s_helmet"
  },
  "pattern": [
    "AAA",
    "A A"
  ],
  "type": "forge:ore_shaped",
  "key": {
    "A": {
      "item": "taint:ingot_%(metal)s"
    }
  }
}''' % { 'metal': metal })
    with open(metal+'_sword.json', 'w', encoding='utf-8') as file:
        file.write('''{
  "result": {
    "item": "taint:%(metal)s_sword"
  },
  "pattern": [
    "A",
    "A",
    "S"
  ],
  "type": "forge:ore_shaped",
  "key": {
    "A": {
      "item": "taint:ingot_%(metal)s"
    },
    "S": {
      "item": "minecraft:stick"
    }
  }
}''' % { 'metal': metal })
    with open(metal+'_shovel.json', 'w', encoding='utf-8') as file:
        file.write('''{
  "result": {
    "item": "taint:%(metal)s_shovel"
  },
  "pattern": [
    "A",
    "S",
    "S"
  ],
  "type": "forge:ore_shaped",
  "key": {
    "A": {
      "item": "taint:ingot_%(metal)s"
    },
    "S": {
      "item": "minecraft:stick"
    }
  }
}''' % { 'metal': metal })
    with open(metal+'_pickaxe.json', 'w', encoding='utf-8') as file:
        file.write('''{
  "result": {
    "item": "taint:%(metal)s_pickaxe"
  },
  "pattern": [
    "AAA",
    " S ",
    " S "
  ],
  "type": "forge:ore_shaped",
  "key": {
    "A": {
      "item": "taint:ingot_%(metal)s"
    },
    "S": {
      "item": "minecraft:stick"
    }
  }
}''' % { 'metal': metal })
    with open(metal+'_axe.json', 'w', encoding='utf-8') as file:
        file.write('''{
  "result": {
    "item": "taint:%(metal)s_axe"
  },
  "pattern": [
    "AA",
    "AS",
    " S"
  ],
  "type": "forge:ore_shaped",
  "key": {
    "A": {
      "item": "taint:ingot_%(metal)s"
    },
    "S": {
      "item": "minecraft:stick"
    }
  }
}''' % { 'metal': metal })
    with open(metal+'_hoe.json', 'w', encoding='utf-8') as file:
        file.write('''{
  "result": {
    "item": "taint:%(metal)s_hoe"
  },
  "pattern": [
    "AA",
    " S",
    " S"
  ],
  "type": "forge:ore_shaped",
  "key": {
    "A": {
      "item": "taint:ingot_%(metal)s"
    },
    "S": {
      "item": "minecraft:stick"
    }
  }
}''' % { 'metal': metal })
