import sys

metalNames = [
    "ardorum",
    "termium",
    "adipatum",
    "caersin",
    "neulite",
    "atercaeum",
    "oscurum",
    "inurose",
    "cibarite"
]

model_t = "{{\n  \"parent\": \"item/generated\",\n    \"textures\": {{\n      \"layer0\": \"taint:items/{name}\"\n    }}\n  }}"
model_f = "./models/item/{name}.json"
for s in metalNames:
    for t in ["helmet","chestplate","boots","leggings","sword","axe","hoe","shovel","pickaxe"]:
        f = open(model_f.format(name="{string}_{type}".format(string=s, type=t)), "w")
        f.write(model_t.format(name="{string}_{type}".format(string=s, type=t)))
        f.close()

