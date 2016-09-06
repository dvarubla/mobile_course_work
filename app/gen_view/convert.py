import xml.etree.ElementTree as ElTree
import re
from string import Template

import sys

XML_FILE = "image.xml"
JAVA_FILE = "../src/main/java/study/courseproject/task2/SnowmanGenView.java"

names_whitelist = ["ball1", "ball2", "ball3", "ball4"]

with open("templates.txt", "rt") as f:
    templates_txt = f.read()

templates_txt = re.sub("(?<=\n) +\\\\(?=\$)", "", templates_txt)

templates = dict(
    map(
        lambda a: (a[0], Template(a[1])),
        re.findall("([_a-z]+):\n(.+?) *\n(?: *\n###|$)", templates_txt, flags=re.S | re.I)
    )
)

try:
    tree = ElTree.parse(XML_FILE)
except FileNotFoundError:
    sys.exit("File %s not found!" % XML_FILE)

for el in tree.iter():
    if hasattr(el, 'attrib'):
        el.attrib = dict(map(lambda m: (re.sub("{.*}", "", m[0]), m[1]), el.attrib.items()))

root = tree.getroot()

draw_methods = []
draw_calls = []

width = root.attrib["width"][:-2] + "f"
height = root.attrib["height"][:-2] + "f"


def process_color(color_str: str) -> str:
    color_str = color_str[1:]
    if len(color_str) == 6:
        color_str = "ff" + color_str
    return "0x" + color_str


id_counter = 0

for path in root.iter('path'):
    firstPnt = {"x": 0, "y": 0}
    pathFrags = re.findall(
        "([a-z]+) *([^a-z]*)(?: +|$)",
        path.attrib["pathData"],
        flags=re.I
    )
    path_calls = []
    for name, values in pathFrags:
        values = list(map(lambda v: v + "f", re.split(" |,", values)))
        if name == "M":
            firstPnt["x"], firstPnt["y"] = values
            path_calls.append(
                templates["move_call"].substitute(args=", ".join(values))
            )
        elif name == "C":
            path_calls.append(
                templates["cubicTo_call"].substitute(args=", ".join(values))
            )
        elif name == "Z":
            path_calls.append(
                templates["setLastPoint_call"].substitute(args=str(firstPnt["x"]) + ", " + str(firstPnt["y"]))
            )
        elif name == "L":
            path_calls.append(
                templates["lineTo_call"].substitute(args=", ".join(values))
            )
    rec_args = []
    sent_args = []
    if path.attrib["name"] in names_whitelist:
        name = path.attrib["name"].replace("-", "_")
    else:
        name = id_counter
        id_counter += 1
    draw = False
    fill = False
    if "strokeColor" in path.attrib:
        rec_args.extend(("int strokeColor", "float strokeWidth"))
        sent_args.extend((process_color(path.attrib["strokeColor"]), path.attrib["strokeWidth"] + "f"))
        draw = True
    if "fillColor" in path.attrib:
        rec_args.append("int fillColor")
        sent_args.append(process_color(path.attrib["fillColor"]))
        fill = True

    if draw and fill:
        path_calls.append(templates["draw_and_fill_path"].substitute())
    elif draw:
        path_calls.append(templates["draw_path"].substitute())
    else:
        path_calls.append(templates["fill_path"].substitute())

    draw_calls.append(
        templates["draw_call"].substitute(
            name=name, args=", " + (", ".join(sent_args))
        )
    )

    draw_methods.append(templates["draw_method"].substitute(
        args=", " + (", ".join(rec_args)),
        name=name,
        path_calls="\n".join(path_calls))
    )

result = templates["class"].substitute(
    width=width,
    height=height,
    draw_calls="\n".join(draw_calls),
    draw_methods="\n\n".join(draw_methods)
)

with open(JAVA_FILE, "wt") as f:
    f.write(result)

print("Successfully generated %s" % JAVA_FILE)
