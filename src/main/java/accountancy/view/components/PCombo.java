package accountancy.view.components;


import accountancy.model.Entity;
import accountancy.view.config.Colors;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxEditor;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;

public class PCombo extends JComboBox<Entity> {

    protected JTextField inputField;

    public PCombo() {

        super();
        this.setBackground(Colors.BACKGROUND);
        this.setForeground(Colors.TEXT);

        BasicComboBoxEditor editor = new ComboBoxEditor();
        if (editor.getEditorComponent() instanceof JTextField) {
            inputField = (JTextField) editor.getEditorComponent();
            Listener listener = new Listener();
            inputField.addActionListener(listener);
            inputField.addFocusListener(listener);
            inputField.addKeyListener(listener);
        }

        this.setEditor(editor);
        this.setEditable(true);
        this.setRenderer(new ComboBoxRenderer());
        this.setUI(new BasicComboBoxUI() {
            @Override
            protected JButton createArrowButton() {

                return new PComboButton();
            }
        });
    }

    public Object getValue() {

        return inputField.getText();
    }

    class Listener extends AbstractMultiListener {

        private int previousSelectedLength = 0;

        public void actionPerformed(ActionEvent e) {

            hidePopup();
            processEvent(e);
        }

        public void focusGained(FocusEvent e) {

            showPopup();
            inputField.setCaretPosition(0);
            inputField.setSelectionStart(0);
            int selectionEnd = inputField.getText().length();
            inputField.setSelectionEnd(selectionEnd);
        }

        public void focusLost(FocusEvent e) {

            hidePopup();
            processEvent(e);
        }

        public void keyReleased(KeyEvent ev) {

            char key = ev.getKeyChar();
            if (!(Character.isLetterOrDigit(key) || Character.isSpaceChar(key) || ev.getKeyCode() == 8)) {
                this.previousSelectedLength = inputField.getSelectionEnd() - inputField.getSelectionStart();
                return;
            }

            showPopup();

            if (ev.getKeyCode() == 8) {

                try {
                    if (inputField.getText().length() > 0 && this.previousSelectedLength > 0) {
                        inputField.setText(inputField.getText(0, inputField.getText().length() - 1));
                    }
                } catch (BadLocationException e) {
                    System.out.println(e.getMessage());
                }
            }

            int    caretPos = inputField.getCaretPosition();
            String text     = "";
            try {
                text = inputField.getText(0, caretPos);
            } catch (BadLocationException e) {
                System.out.println(e.getMessage());
            }

            for (int i = 0 ; i < getItemCount() ; i++) {
                String element = getItemAt(i).toString();
                if (element.startsWith(text)) {
                    setSelectedIndex(i);
                    inputField.setText(getItemAt(i).toString());
                    int selectionStart = Math.min(inputField.getText().length(), caretPos);
                    inputField.setCaretPosition(selectionStart);
                    inputField.setSelectionStart(selectionStart);
                    int selectionEnd = inputField.getText().length();
                    inputField.setSelectionEnd(selectionEnd);
                    this.previousSelectedLength = selectionEnd - selectionStart;
                    return;
                }
            }
        }

    }

    class ComboBoxEditor extends BasicComboBoxEditor {

        private final JTextField label = new PTextField();
        private       JPanel     panel = new JPanel();
        private Object selectedItem;

        public ComboBoxEditor() {

            label.setOpaque(true);
            label.setForeground(Colors.TEXT);
            label.setBackground(Colors.BACKGROUND);
        }

        public Component getEditorComponent() {

            return this.label;
        }

        public Object getItem() {

            return this.selectedItem;
        }

        public void setItem(Object item) {

            this.selectedItem = item;
            if (item != null) {
                label.setText(item.toString());
            }
        }
    }

    class ComboBoxRenderer extends JLabel implements ListCellRenderer<Entity> {

        public ComboBoxRenderer() {

            setOpaque(true);
            setHorizontalAlignment(LEFT);
            setVerticalAlignment(CENTER);
        }


        @Override public Component getListCellRendererComponent(
            JList<? extends Entity> list, Entity value, int index, boolean isSelected, boolean cellHasFocus
        ) {

            if (isSelected) {
                setBackground(Colors.GRADIENT);
                setForeground(Colors.BACKGROUND);
                setBorder(BorderFactory.createEmptyBorder());
            }
            else {
                setBackground(Colors.BACKGROUND);
                setForeground(Colors.TEXT);
                setBorder(BorderFactory.createEmptyBorder());
            }

            setText(value.toString());

            return this;
        }
    }
}
