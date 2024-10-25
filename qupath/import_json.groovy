import qupath.lib.objects.PathObject
import qupath.lib.objects.PathAnnotationObject
import qupath.lib.roi.*
import qupath.lib.io.GsonTools
import com.google.gson.reflect.TypeToken

// Define the path to your JSON file
def jsonPath = buildFilePath(PROJECT_BASE_DIR, 'annotations', '2024-08-05-11-35-42-H 340 CD 410 430 BD 44 ID 186_annotations.json')

// Read the JSON file
def file = new File(jsonPath)
if (!file.exists()) {
    print "JSON file not found: " + jsonPath
    return
}

def jsonText = file.text

// Parse JSON into a list of PathAnnotationObjects
def gson = GsonTools.getInstance(true)
def listType = new TypeToken<List<PathAnnotationObject>>() {}.getType()
def annotations = gson.fromJson(jsonText, listType)

// Add annotations to the current image
def imageData = getCurrentImageData()
def hierarchy = imageData.getHierarchy()

annotations.each { annotation ->
    hierarchy.addPathObject(annotation)
}

// Refresh the viewer to display the new annotations
fireHierarchyUpdate()

print "Annotations imported from: " + jsonPath
