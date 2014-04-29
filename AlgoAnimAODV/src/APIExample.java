
	import java.awt.Color;
	import java.awt.Font;

	import algoanim.animalscript.AnimalScript;
	import algoanim.exceptions.LineNotExistsException;
	import algoanim.primitives.ArrayMarker;
	import algoanim.primitives.IntArray;
	import algoanim.primitives.SourceCode;
	import algoanim.primitives.generators.Language;
	import algoanim.properties.AnimationPropertiesKeys;
	import algoanim.properties.ArrayMarkerProperties;
	import algoanim.properties.ArrayProperties;
	import algoanim.properties.SourceCodeProperties;
	import algoanim.util.Coordinates;

	/**
	 * @author Dr. Guido R&ouml;&szlig;ling <roessling@acm.org>
	 * @version 1.0 2007-05-30
	 *
	 */
	public class APIExample {
		
	    /**
	     * The concrete language object used for creating output
	     */
	    private Language lang;
		
	    /**
	     * Default constructor
	     * @param l the conrete language object used for creating output
	     */
	    public APIExample(Language l) {
		// Store the language object
		lang = l;
		// This initializes the step mode. Each pair of subsequent steps has to
		// be divdided by a call of lang.nextStep();
		lang.setStepMode(true);
	    }
		
	    private static final String DESCRIPTION = 
		"QuickSort w�hlt ein Element aus der zu sortierenden Liste aus "
		+"(Pivotelement) und zerlegt die Liste in zwei Teillisten, eine untere, "
		+"die alle Elemente kleiner und eine obere, die alle Elemente gleich oder "
		+"gr��er dem Pivotelement enth�lt.\nDazu wird zun�chst ein Element von unten "
		+"gesucht, das gr��er als (oder gleichgro� wie) das Pivotelement und damit "
		+"f�r die untere Liste zu gro� ist. Entsprechend wird von oben ein kleineres "
		+"Element als das Pivotelement gesucht. Die beiden Elemente werden dann "
		+"vertauscht und landen damit in der jeweils richtigen Liste.\nDer Vorgang "
		+"wird fortgesetzt, bis sich die untere und obere Suche treffen. Damit sind "
		+"die oben erw�hnten Teillisten in einem einzigen Durchlauf entstanden. "
		+"Suche und Vertauschung k�nnen in-place durchgef�hrt werden."
		+"\n\nDie noch unsortierten Teillisten werden �ber denselben Algorithmus "
		+"in noch kleinere Teillisten zerlegt (z. B. mittels Rekursion) und, sobald "
		+"nur noch Listen mit je einem Element vorhanden sind, wieder zusammengesetzt. "
		+"Die Sortierung ist damit abgeschlossen.";
		
	    private static final String SOURCE_CODE = "public void quickSort(int[] array, int l, int r)" // 0
		+ "\n{" // 1
		+ "\n  int i, j, pivot;" // 2
		+ "\n  if (r>l)" // 3
		+ "\n  {" // 4
		+ "\n    pivot = array[r];" // 5
		+ "\n    for (i = l; j = r - 1; i < j; )" // 6
		+ "\n    {" // 7
		+ "\n      while (array[i] <= pivot && j > i)" // 8
		+ "\n        i++;" // 9
		+ "\n      while (pivot < array[j] && j > i)" // 10
		+ "\n        j--;" // 11
		+ "\n      if (i < j)" // 12
		+ "\n        swap(array, i, j);" // 13
		+ "\n    }" // 14
		+ "\n    if (pivot < array[i])" // 15
		+ "\n      swap(array, i, r);" // 16
		+ "\n    else" // 17
		+ "\n      i=r;" // 18
		+ "\n    quickSort(array, l, i - 1);" // 19
		+ "\n    quickSort(array, i + 1, r);" // 20
		+ "\n  }" // 21
		+ "\n}"; // 22
		
	    /**
	     * Sort the int array passed in
	     * @param a the array to be sorted
	     */
	    public void sort(int[] a) {
		// Create Array: coordinates, data, name, display options, 
		// default properties
			
		// first, set the visual properties (somewhat similar to CSS)
		ArrayProperties arrayProps = new ArrayProperties();
		arrayProps.set(AnimationPropertiesKeys.COLOR_PROPERTY, Color.BLACK);
		arrayProps.set(AnimationPropertiesKeys.FILL_PROPERTY, Color.WHITE);
		arrayProps.set(AnimationPropertiesKeys.FILLED_PROPERTY, Boolean.TRUE);   
		arrayProps.set(AnimationPropertiesKeys.ELEMENTCOLOR_PROPERTY, 
			       Color.BLACK);
		arrayProps.set(AnimationPropertiesKeys.ELEMHIGHLIGHT_PROPERTY, 
			       Color.RED);
		arrayProps.set(AnimationPropertiesKeys.CELLHIGHLIGHT_PROPERTY, 
			       Color.YELLOW);
		    
		// now, create the IntArray object, linked to the properties
		IntArray ia = lang.newIntArray(new Coordinates(20, 100), a, "intArray", 
					       null, arrayProps);
		    
		// start a new step after the array was created
		lang.nextStep();		
		    
		// Create SourceCode: coordinates, name, display options, 
		// default properties
		    
		// first, set the visual properties for the source code
		SourceCodeProperties scProps = new SourceCodeProperties();
		scProps.set(AnimationPropertiesKeys.CONTEXTCOLOR_PROPERTY, Color.BLUE);
		scProps.set(AnimationPropertiesKeys.FONT_PROPERTY, new Font("Monospaced", 
									    Font.PLAIN, 12));
		    
		scProps.set(AnimationPropertiesKeys.HIGHLIGHTCOLOR_PROPERTY, 
			    Color.RED);   
		scProps.set(AnimationPropertiesKeys.COLOR_PROPERTY, Color.BLACK);
		    
		// now, create the source code entity
		SourceCode sc = lang.newSourceCode(new Coordinates(40, 140), "sourceCode",
						   null, scProps);
		    
		// Add the lines to the SourceCode object.
		// Line, name, indentation, display dealy
		sc.addCodeLine("public void quickSort(int[] array, int l, int r)", null, 0, null);  // 0
		sc.addCodeLine("{", null, 0, null); 
		sc.addCodeLine("int i, j, pivot;", null, 1, null); 
		sc.addCodeLine("if (r>l)", null, 1, null);  // 3
		sc.addCodeLine("{", null, 1, null);  // 4
		sc.addCodeLine("pivot = array[r];", null, 2, null);  // 5
		sc.addCodeLine("for (i = l; j = r - 1; i < j; )", null, 2, null);  // 6
		sc.addCodeLine("{", null, 2, null); // 7
		sc.addCodeLine("while (array[i] <= pivot && j > i)", null, 3, null); // 8
		sc.addCodeLine("i++;", null, 4, null); // 9
		sc.addCodeLine("while (pivot < array[j] && j > i)", null, 3, null); // 10
		sc.addCodeLine("j--;", null, 4, null); // 11
		sc.addCodeLine("if (i < j)", null, 3, null); // 12
		sc.addCodeLine("swap(array, i, j);", null, 4, null); // 13
		sc.addCodeLine("}", null, 2, null); // 14
		sc.addCodeLine("if (pivot < array[i])", null, 2, null); // 15
		sc.addCodeLine("swap(array, i, r);", null, 3, null); // 16
		sc.addCodeLine("else", null, 2, null); // 17
		sc.addCodeLine("i=r;", null, 3, null); // 18
		sc.addCodeLine(" quickSort(array, l, i - 1);", null, 2, null); // 19
		sc.addCodeLine(" quickSort(array, i + 1, r);", null, 2, null); // 20
		sc.addCodeLine(" }", null, 1, null); // 21
		sc.addCodeLine("}", null, 0, null); // 22
		    
		lang.nextStep();
		// Highlight all cells
		ia.highlightCell(0, ia.getLength() - 1, null, null);
		try {
		    // Start quicksort
		    quickSort(ia, sc, 0, (ia.getLength() - 1));
		} catch (LineNotExistsException e) {
		    e.printStackTrace();
		} 
		sc.hide();
		ia.hide();
		lang.nextStep();
	    }
	    
	    /**
	     * counter for the number of pointers
	     *
	     */
	    private int pointerCounter = 0;
	    
	    /**
	     * Quicksort: Sort elements using a pivot element between [l, r]
	     * 
	     * @param array the IntArray to be sorted
	     * @param codeSupport the underlying code instance
	     * @param l the lower border of the subarray to be sorted
	     * @param l the upper border of the subarray to be sorted
	     */
	    private void quickSort(IntArray array, SourceCode codeSupport, int l, int r) 
		throws LineNotExistsException {
		// Highlight first line
		// Line, Column, use context colour?, display options, duration
		codeSupport.highlight(0, 0, false);
		lang.nextStep();
			
		// Highlight next line
		codeSupport.toggleHighlight(0, 0, false, 2, 0);
			
		// Create two markers to point on i and j
		pointerCounter++;
		// Array, current index, name, display options, properties
		ArrayMarkerProperties arrayIMProps = new ArrayMarkerProperties();
		arrayIMProps.set(AnimationPropertiesKeys.LABEL_PROPERTY, "i");   
		arrayIMProps.set(AnimationPropertiesKeys.COLOR_PROPERTY, Color.BLACK);
		ArrayMarker iMarker = lang.newArrayMarker(array, 0, "i" + pointerCounter, 
							  null, arrayIMProps);
		pointerCounter++;
			
		ArrayMarkerProperties arrayJMProps = new ArrayMarkerProperties();
		arrayJMProps.set(AnimationPropertiesKeys.LABEL_PROPERTY, "j");   
		arrayJMProps.set(AnimationPropertiesKeys.COLOR_PROPERTY, Color.BLACK);
		ArrayMarker jMarker = lang.newArrayMarker(array, 0, "j" + pointerCounter, 
							  null, arrayJMProps);

		int i, j;
			
		lang.nextStep();
		// Highlight next line
		codeSupport.toggleHighlight(2, 0, false, 3, 0);
		// this statement is equivalent to
		//		codeSupport.unhighlight(2, 0, false);
		//		codeSupport.highlight(3, 0, false);

		// Create a marker for the pivot element
		int pivot;
		pointerCounter++;
		ArrayMarkerProperties arrayPMProps = new ArrayMarkerProperties();
		arrayPMProps.set(AnimationPropertiesKeys.LABEL_PROPERTY, "pivot");   
		arrayPMProps.set(AnimationPropertiesKeys.COLOR_PROPERTY, Color.BLUE);

		ArrayMarker pivotMarker = lang.newArrayMarker(array, 0, 
							      "pivot" + pointerCounter, null, arrayPMProps);
			
		lang.nextStep();
		codeSupport.unhighlight(3, 0, false);
		if (r > l) {
		    lang.nextStep();
		    // Highlight next line
		    codeSupport.highlight(5, 0, false);
				
		    // Receive the value of the pivot element
		    pivot = array.getData()[r];
		    // Move marker to that position
		    pivotMarker.move(r, null, null);
				
				
		    lang.nextStep();
		    codeSupport.unhighlight(5, 0, false);
		    for (i = l, j = r - 1; i < j;) {
			// Highlight next line
			codeSupport.highlight(6, 0, false);
			// Move the two markers i,j to their proper positions
			iMarker.move(i, null, null);
			jMarker.move(j, null, null);
						
			lang.nextStep();
			// Highlight next line
			codeSupport.toggleHighlight(6, 0, false, 8, 0);
					
			while (array.getData()[i] <= pivot && j > i) {
			    lang.nextStep();
			    i++;
						
			    // Highlight next line
			    codeSupport.toggleHighlight(8, 0, false, 9, 0);
			    // Move marker i to its next position
			    iMarker.move(i, null, null);
						
			    lang.nextStep();
			    // Highlight next line
			    codeSupport.toggleHighlight(9, 0, false, 8, 0);
			}

			lang.nextStep();
			// Highlight next line
			codeSupport.toggleHighlight(8, 0, false, 10, 0);
			while (pivot < array.getData()[j] && j > i) {
			    lang.nextStep();

			    j--;
			    // Highlight next line
			    codeSupport.toggleHighlight(10, 0, false, 11, 0);

			    // Move marker j to its next position
			    jMarker.move(j, null, null);
						
			    lang.nextStep();
			    // Highlight next line
			    codeSupport.toggleHighlight(11, 0, false, 10, 0);

			}
					
			lang.nextStep();
			// Highlight next line
			codeSupport.toggleHighlight(10, 0, false, 12, 0);

					
			if (i < j) {
			    lang.nextStep();
			    // Highlight next line
			    codeSupport.toggleHighlight(12, 0, false, 13, 0);

						
			    // Swap the array elements at position i and j
			    array.swap(i, j, null, null);
			}
			lang.nextStep();
			// Highlight next line
			codeSupport.toggleHighlight(13, 0, false, 12, 0);

		    } // end for...
		    // Highlight next line
		    codeSupport.toggleHighlight(6, 0, false, 13, 0);

				
		    lang.nextStep();
		    if (pivot < array.getData()[i]) {
			// Highlight next line
			codeSupport.toggleHighlight(15, 0, false, 16, 0);

			// Swap the array elements at position i and r
			array.swap(i, r, null, null);
			// Set pivot marker to position i
			pivotMarker.move(i, null, null);
					
			lang.nextStep();
			codeSupport.unhighlight(16, 0, false);
		    } else {
			i = r;
			// Highlight next line
			codeSupport.toggleHighlight(15, 0, false, 18, 0);
			// Move marker i to position r
			iMarker.move(r, null, null);
					
			lang.nextStep();
			codeSupport.unhighlight(18, 0, false);
		    }
		    // Highlight the i'th array element
		    array.highlightElem(i, null, null);
				
		    lang.nextStep();
		    codeSupport.highlight(19, 0, false);
				
		    lang.nextStep();
		    codeSupport.unhighlight(19, 0, false);
				
		    // Unhighlight cells from i to r
		    // this part is not scheduled...
		    array.unhighlightCell(i, r, null, null); 
		    // Apply quicksort to the left array part
		    iMarker.hide();
		    jMarker.hide();
		    pivotMarker.hide();
		    quickSort(array, codeSupport, l, i - 1);
		    iMarker.show();
		    jMarker.show();
		    pivotMarker.show();
				
		    // Left recursion finished.
		    lang.nextStep();
		    // Highlight cells l to r
		    array.highlightCell(l, r, null, null);
		    codeSupport.highlight(20, 0, false);
				
		    lang.nextStep();
		    codeSupport.unhighlight(20, 0, false);
		    // Unhighlight cells l to i
		    array.unhighlightCell(l, i, null, null);
		    // Apply quicksort to the right array part
		    iMarker.hide();
		    jMarker.hide();
		    pivotMarker.hide();
		    quickSort(array, codeSupport, i + 1, r);
		    iMarker.show();
		    jMarker.show();
		    pivotMarker.show();
		}
		lang.nextStep();
		// Highlight next line
		codeSupport.highlight(21, 0, false);
		lang.nextStep();
		// Highlight next line
		codeSupport.highlight(22, 0, false);
			
		lang.nextStep();
		// Unhighlight cells from l to r
		array.unhighlightCell(l, r, null, null);
		lang.nextStep();
		iMarker.hide();
		jMarker.hide();
		pivotMarker.hide();
	    }
		
	    protected String getAlgorithmDescription() {
		return DESCRIPTION;
	    }
		
	    protected String getAlgorithmCode() {
		return SOURCE_CODE;
	    }
		
	    public String getName() {
		return "Quicksort (pivot=last)";
	    }
		
	    public String getDescription() {
		return DESCRIPTION;
	    }
		
	    public String getCodeExample() {
		return SOURCE_CODE;
	    }
		
	    public static void main(String[] args) {
		// Create a new animation
		// name, author, screen width, screen height
		Language l = new AnimalScript("Quicksort Animation", "Dr. Guido R��ling", 640, 480);
			APIExample s = new APIExample(l);
			int[] a = {7,3,2,4,1,13,52,13,5,1};
			s.sort(a);
			System.out.println(l);
		}
	}

