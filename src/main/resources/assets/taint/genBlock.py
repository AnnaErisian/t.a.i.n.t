import sys

blockstate = "{{\n  \"variants\": {{\n    \"normal\": [\n      {{ \"model\": \"taint:{name}\" }}\n    ]\n  }}\n}}"
model = "{{\n  \"parent\": \"block/cube_all\",\n  \"textures\": {{\n    \"all\": \"taint:blocks/{name}\"\n  }}\n}}"
item_model = "{{\n  \"parent\": \"taint:block/{name}\"\n}}"
blockstate_f = "./blockstates/{name}.json"
model_f = "./models/block/{name}.json"
item_model_f = "./models/item/{name}.json"
for string in sys.argv[1:]:
    f = open(blockstate_f.format(name=string), "w")
    f.write(blockstate.format(name=string))
    f.close()

    f = open(model_f.format(name=string), "w")
    f.write(model.format(name=string))
    f.close()

    f = open(item_model_f.format(name=string), "w")
    f.write(item_model.format(name=string))
    f.close()


