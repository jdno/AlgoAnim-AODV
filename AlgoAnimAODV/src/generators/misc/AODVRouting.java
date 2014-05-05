package generators.misc;

import generators.framework.Generator;
import generators.framework.GeneratorType;
import java.util.Locale;
import algoanim.primitives.generators.Language;
import java.util.Hashtable;
import generators.framework.properties.AnimationPropertiesContainer;
import algoanim.animalscript.AnimalScript;

public class AODVRouting implements Generator {
    private Language lang;
    private int[][] adjacencyMatrix;

    public void init(){
        lang = new AnimalScript("Ad-hoc Optimized Vector Routing", "Sascha Bleidner, Jan David Nose", 800, 600);
    }

    public String generate(AnimationPropertiesContainer props,Hashtable<String, Object> primitives) {
        adjacencyMatrix = (int[][])primitives.get("adjacencyMatrix");
        
        return lang.toString();
    }

    public String getName() {
        return "Ad-hoc Optimized Vector Routing";
    }

    public String getAlgorithmName() {
        return "AODV";
    }

    public String getAnimationAuthor() {
        return "Sascha Bleidner, Jan David Nose";
    }

    public String getDescription(){
        return "AODV ist ein reaktiver Routingalgorithmus f??r mobile Ad-hoc Netze.";
    }

    public String getCodeExample(){
        return "";
    }

    public String getFileExtension(){
        return "asu";
    }

    public Locale getContentLocale() {
        return Locale.GERMAN;
    }

    public GeneratorType getGeneratorType() {
        return new GeneratorType(GeneratorType.GENERATOR_TYPE_MORE);
    }

    public String getOutputLanguage() {
        return Generator.JAVA_OUTPUT;
    }

}