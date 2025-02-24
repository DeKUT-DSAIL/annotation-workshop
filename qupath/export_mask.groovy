def imageData = getCurrentImageData()

// Define output path (relative to project)
def outputDir = buildFilePath(PROJECT_BASE_DIR, 'masks')
mkdirs(outputDir)
def name = GeneralTools.getNameWithoutExtension(imageData.getServer().getMetadata().getName())
def path = buildFilePath(outputDir, name + "_mask.png")

// Define how much to downsample during export (may be required for large images)
double downsample = 8

// Create an ImageServer where the pixels are derived from annotations
def labelServer = new LabeledImageServer.Builder(imageData)
  .backgroundLabel(0, ColorTools.BLACK) // Specify background label (usually 0 or 255)
  .addLabel('Tree', 255, ColorTools.WHITE)      // Choose output labels (the order matters!)
  .multichannelOutput(false) // If true, each label refers to the channel of a multichannel binary image (required for multiclass probability)
  .build()

// Write the image
writeImage(labelServer, path)
