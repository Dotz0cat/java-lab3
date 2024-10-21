public record IrisData(double sepalLength, double sepalWidth, double petalLength, double petalWidth, String variety) {
   public static final String[] dataNames = {
           "Sepal Length", "Sepal Width", "Petal Length", "Petal Width", "Variety"
   };

   public static IrisData IrisDataMap(String line) {
      String[] parts = line.split(",");

      double sepalLength = Double.parseDouble(parts[0].trim());
      double sepalWidth = Double.parseDouble(parts[1].trim());
      double petalLength = Double.parseDouble(parts[2].trim());
      double petalWidth = Double.parseDouble(parts[3].trim());
      String variety = parts[4].replace('"', ' ').trim();

      return new IrisData(sepalLength, sepalWidth, petalLength, petalWidth, variety);
   }
}

