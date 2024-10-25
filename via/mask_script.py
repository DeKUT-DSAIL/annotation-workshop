import cv2
import json
import numpy as np

f = open("via_project_24Oct2024_16h20m_json.json")
data = json.load(f)
# print(data.keys())

img_dir = "images"
mask_dir = "masks"

for k, v in data.items():
    filename = v['filename']
    img_path = f"{img_dir}\\{filename}"
    mask_path = f"{mask_dir}/{filename}"
    img = cv2.imread(img_path, cv2.IMREAD_COLOR)
    h, w, _ = img.shape

    mask = np.zeros((h, w))

    regions = v["regions"]
    for region in regions:
        shape_attributes = region["shape_attributes"]
        if shape_attributes["name"] == "polygon":
            x_points = shape_attributes["all_points_x"]
            y_points = shape_attributes["all_points_y"]

            contours = []
            for x, y in zip(x_points, y_points):
                contours.append((x, y))
            contours = np.array(contours)
            cv2.drawContours(mask, [contours], -1, 255, -1)
    
    
    cv2.imwrite(mask_path, mask)