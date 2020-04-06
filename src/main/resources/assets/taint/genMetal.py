import sys

metalNames = [
"accelerase",
"adamantite",
"adipatum",
"aerium",
"antenium",
"ardorum",
"ascalite",
"atercaeum",
"betweensteel",
"black_rosite",
"caersin",
"cannium",
"cibarite",
"dupondium",
"escalite",
"faerite",
"fierkite",
"infernium",
"inurose",
"littium",
"lordslime",
"luginite",
"lunite",
"neulite",
"orichalcum",
"orinase",
"oscurum",
"peractium",
"piridium",
"ptemnium",
"rallium",
"sestertium",
"slipsteel",
"solairite",
"soldrite",
"starsteel",
"superlumium",
"tempestite",
"terium",
"termium",
"unseelium",
"urielium"
]

blockstate = "{{\n  \"variants\": {{\n    \"normal\": [\n      {{ \"model\": \"taint:block_{name}\" }}\n    ]\n  }}\n}}"
model = "{{\n  \"parent\": \"block/cube_all\",\n  \"textures\": {{\n    \"all\": \"taint:blocks/block_{name}\"\n  }}\n}}"
item_model = "{{\n  \"parent\": \"taint:block/block_{name}\"\n}}"
ingot_model = "{{\n  \"parent\": \"item/generated\",\n    \"textures\": {{\n      \"layer0\": \"taint:items/ingot_{name}\"\n    }}\n  }}"
nugget_model = "{{\n  \"parent\": \"item/generated\",\n    \"textures\": {{\n      \"layer0\": \"taint:items/nugget_{name}\"\n    }}\n  }}"
blockstate_f = "./blockstates/block_{name}.json"
model_f = "./models/block/block_{name}.json"
item_block_f = "./models/item/block_{name}.json"
item_ingot_f = "./models/item/ingot_{name}.json"
item_nugget_f = "./models/item/nugget_{name}.json"
for string in metalNames:
    f = open(blockstate_f.format(name=string), "w")
    f.write(blockstate.format(name=string))
    f.close()

    f = open(model_f.format(name=string), "w")
    f.write(model.format(name=string))
    f.close()

    f = open(item_block_f.format(name=string), "w")
    f.write(item_model.format(name=string))
    f.close()

    f = open(item_ingot_f.format(name=string), "w")
    f.write(ingot_model.format(name=string))
    f.close()

    f = open(item_nugget_f.format(name=string), "w")
    f.write(nugget_model.format(name=string))
    f.close()

