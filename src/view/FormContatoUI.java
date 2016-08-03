package view;

import controller.ContatoCTRL;
import helper.FoneHelper;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.ParseException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import model.Contato;

public class FormContatoUI extends JFrame {

    private JLabel lbNome, lbFone;
    private JTextField tfNome;
    private JFormattedTextField ftFone;
    private MaskFormatter mask;
    private JButton btSalvar;
    private DefaultTableModel model;
    private Contato contato;
    
    public FormContatoUI(Contato contato, DefaultTableModel model) {
        this.contato = contato;
        this.model = model;
        setComponents();
        setEvents();
    }

    private void setComponents() {
        setLayout(null);
        setResizable(false);
        setBounds(0, 0, 380, 120);
        
        lbNome = new JLabel("Nome:");
        lbNome.setBounds(10, 10, 80, 25);
        add(lbNome);
        
        tfNome = new JTextField();
        tfNome.setBounds(10, 35, 150, 32);
        add(tfNome);
        
        lbFone = new JLabel("Fone:");
        lbFone.setBounds(170, 10, 80, 25);
        add(lbFone);
        
        try {
            if (contato == null) {
                setIconImage(new ImageIcon("images/icons/add.png").getImage());
                setTitle("Novo Contato");
                mask = new MaskFormatter("(##) ####-####");
                ftFone = new JFormattedTextField(mask);
            } else {
                setIconImage(new ImageIcon("images/icons/edit.png").getImage());
                setTitle("Editar Contato");
                if (FoneHelper.clear(contato.getFone()).length() == 10) {
                    mask = new MaskFormatter("(##) ####-####");
                } else {
                    mask = new MaskFormatter("(##) #####-####");
                }
                ftFone = new JFormattedTextField(mask);
                tfNome.setText(contato.getNome());
                ftFone.setText(contato.getFone());
            }
        } catch (ParseException error) {
            System.out.println("ERRO: " + error.toString());
        } finally {
            ftFone.setBounds(170, 35, 130, 32);
            add(ftFone);
        }
        
        btSalvar = new JButton(new ImageIcon("images/icons/save.png"));
        btSalvar.setBounds(320, 35, 32, 32);
        btSalvar.setBackground(new Color(238, 238, 238));
        btSalvar.setBorder(null);
        add(btSalvar);
        
    }

    private void setEvents() {
        btSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tfNome.getText().equals("") || FoneHelper.clear(ftFone.getText()).equals("")) {
                    JOptionPane.showMessageDialog(null, "Campos obrigatórios");
                } else {
                    ContatoCTRL control = new ContatoCTRL();
                    if (contato == null) {
                        if (control.adcionar(tfNome.getText(), FoneHelper.clear(ftFone.getText()))) {
                            loadTable();
                            setVisible(false);
                        } else {
                            JOptionPane.showMessageDialog(null, "Falha ao tentar adcionar");
                        }
                    } else {
                        if (control.atualizar(contato.getId(), tfNome.getText(), FoneHelper.clear(ftFone.getText()))) {
                            loadTable();
                            setVisible(false);
                        } else {
                            JOptionPane.showMessageDialog(null, "Falha ao tentar atualizar");
                        }
                    }
                }
            }  
        });
        ftFone.addKeyListener(new KeyListener() {
            @Override
            public void keyReleased(KeyEvent e) {
                try {
                    String fone = FoneHelper.clear(ftFone.getText());
                    if (FoneHelper.clear(ftFone.getText()).substring(2, 3).equals("9")) {
                        mask = new MaskFormatter("(##) #####-####");
                        ftFone.setFormatterFactory(new DefaultFormatterFactory(mask));
                        ftFone.setText(fone);
                    } else {
                        mask = new MaskFormatter("(##) ####-####");
                        ftFone.setFormatterFactory(new DefaultFormatterFactory(mask));
                        ftFone.setText(fone);
                    }
                } catch (StringIndexOutOfBoundsException | ParseException error) {}
            }
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {}
        });
    }
    
    private void loadTable() {
        model.setRowCount(0);
        for (Contato contato : new ContatoCTRL().listar(null)) {
            model.addRow(new Object[] {contato.getId(), contato.getNome(), FoneHelper.format(contato.getFone())});
        }
    }
    
    public void open() {
        FormContatoUI frame = new FormContatoUI(contato, model);
        Dimension window = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (window.width - frame.getSize().width) / 2;
        int y = (window.height - frame.getSize().height) / 2;
        frame.setLocation(x, y);
        frame.setVisible(true);
    }

}
    
    

