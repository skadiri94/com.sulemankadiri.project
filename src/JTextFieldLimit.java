
import java.awt.*;
import javax.swing.*;
import javax.swing.text.*;

    class JTextFieldLimit extends JFrame{

        public JTextFieldLimit(JTextField jt) {

                    jt = FilteredField();

            }


        public JTextField FilteredField() {
            JTextField textfield = new JTextField(7);
            AbstractDocument document = (AbstractDocument) textfield.getDocument();
            final int maxCharacters = 1;
            document.setDocumentFilter(new DocumentFilter() {
                public void replace(FilterBypass fb, int offs, int length,
                                    String str, AttributeSet a) throws BadLocationException {

                    String text = fb.getDocument().getText(0,
                            fb.getDocument().getLength());
                    text += str;
                    if ((fb.getDocument().getLength() + str.length() - length) <= maxCharacters
                            && text.matches("^[1-9]")) {
                        super.replace(fb, offs, length, str, a);
                    } else {
                        Toolkit.getDefaultToolkit().beep();
                    }
                }

                public void insertString(FilterBypass fb, int offs, String str,
                                         AttributeSet a) throws BadLocationException {

                    String text = fb.getDocument().getText(0,
                            fb.getDocument().getLength());
                    text += str;
                    if ((fb.getDocument().getLength() + str.length()) <= maxCharacters
                            && text.matches("^[1-9]")) {
                        super.insertString(fb, offs, str, a);
                    } else {
                        Toolkit.getDefaultToolkit().beep();
                    }
                }
            });
            return textfield;
        }



}
