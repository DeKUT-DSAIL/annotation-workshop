import qupath.lib.objects.PathObject
import qupath.lib.objects.PathAnnotationObject
import qupath.lib.io.GsonTools
import qupath.lib.regions.RegionRequest
import com.google.gson.GsonBuilder

// Get the current image data and hierarchy
def imageData = getCurrentImageData()
def hierarchy = imageData.getHierarchy()

// Get all annotations in the image
def annotations = hierarchy.getAnnotationObjects()

// Define the output path (relative to project folder)
def outputDir = buildFilePath(PROJECT_BASE_DIR, 'annotations')
mkdirs(outputDir)
def name = GeneralTools.getNameWithoutExtension(imageData.getServer().getMetadata().getName())
def path = buildFilePath(outputDir, name + "_annotations.json")

// Convert annotations to JSON
def gson = GsonTools.getInstance(true)
def json = gson.toJson(annotations)

// Write the JSON to file
def file = new File(path)
file.text = json

print "Annotations saved to: " + path
