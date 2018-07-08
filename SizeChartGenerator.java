import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.lang.Math;
import java.lang.StringBuilder;

/* GUI application for generating a size chart. The size chart takes in cm as
   and will generate an html table with each field containing both cm/in
 */
public class SizeChartGenerator extends JFrame {

  private static final String SIZE_LABEL = "SIZE";
  // Extendable size labels... just add a label and it will be added to the tool
  private static final String[] SIZE_LABELS = new String[]{"S", "M", "L", "XL", "XXL", "3XL", "4XL"};
  private static int NUMBER_OF_SIZES = SIZE_LABELS.length;
  // Extendable measurement labels... just add a label and it will be added to the tool
  private static final String[] MEASUREMENT_LABELS = new String[]{"CHEST", "SLEEVES", "SHOULDERS", "LENGTH"};
  private static final String GUI_TITLE = "Size Chart Generator";


   public static void main(String[] args) {
      SwingUtilities.invokeLater(new Runnable() {
         @Override
         public void run() {
            new SizeChartGenerator();
         }
      });
   }

   public SizeChartGenerator() {
      Container rootContainer = getContentPane();
      rootContainer.setLayout(new GridLayout(3, 1));

      rootContainer.add(new JLabel("Size chart generator (measurements are in cm)"));

      Container dataContainer = new Container();
      dataContainer.setLayout(new GridLayout(MEASUREMENT_LABELS.length + 1, NUMBER_OF_SIZES + 1));  // The content-pane sets its layout

      dataContainer.add(new JLabel(SIZE_LABEL));
      for (String sizeLabel : SIZE_LABELS) {
        dataContainer.add(new JLabel(sizeLabel));
      }

      final JTextField[][] measurementFields = new JTextField[MEASUREMENT_LABELS.length][NUMBER_OF_SIZES];

      for (int i = 0; i < MEASUREMENT_LABELS.length; i++) {
        dataContainer.add(new JLabel(MEASUREMENT_LABELS[i]));
        measurementFields[i] = getFields(NUMBER_OF_SIZES);
        for (int j = 0; j < NUMBER_OF_SIZES; j++) {
          dataContainer.add(measurementFields[i][j]);
        }
      }

      rootContainer.add(dataContainer);

      final JButton submitButton = new JButton("Generate");
      submitButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          // generate html
          System.out.println("<div class=\"tt-table-responsive-md\">");
          System.out.println("<table id=\"size_chart\" class=\"tt-table-modal-info\"><thead><tr>" + String.format(getMeasurementLabelHeaders()) + "</tr></thead>");

          System.out.println("<tbody>");
          for (int i = 0; i < NUMBER_OF_SIZES; i++) {
            System.out.println("<tr>");
            String label = String.format("<td>%s</td>", SIZE_LABELS[i]);
            System.out.println(label);

            for (int j = 0; j < measurementFields.length; j++) {
              System.out.println(getSizeHtmlForField(measurementFields[j][i]));
            }

            System.out.println("</tr>");
          }
          System.out.println("</tbody>");
          System.out.println("</table>");
          System.out.println("</div>");
        }
      });
      rootContainer.add(submitButton);

      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setTitle(GUI_TITLE);
      setSize(700, 700);
      setVisible(true);
   }

   private static String getMeasurementLabelHeaders() {
     StringBuilder sb = new StringBuilder();
     for (String label : MEASUREMENT_LABELS) {
       sb.append(String.format("<th>%s</th><th>%s</th>", SIZE_LABEL, label));
     }
     return sb.toString();
   }

   private static String getSizeHtmlForField(JTextField field) {
     int sizeCm = 0;
     try {
       sizeCm = Integer.parseInt(field.getText());
     } catch (Exception e) {
       // do nothing
     }

     int sizeIn = (int) Math.ceil(sizeCm * 0.39);
     return sizeCm <= 0 ? "<td></td>" : String.format("<td> %d in <br/> %d cm </td>", sizeIn, sizeCm);
   }

   private static JTextField[] getFields(final int NUMBER_OF_SIZES) {
     JTextField[] sizeFields = new JTextField[NUMBER_OF_SIZES];

     for (int i = 0; i < sizeFields.length; i++) {
       sizeFields[i] = new JTextField();
     }

     return sizeFields;
   }
}
