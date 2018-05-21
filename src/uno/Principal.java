/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uno;

import java.util.Collections;
import java.util.List;
import java.util.Vector;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author USUARIO
 */
public class Principal extends javax.swing.JFrame {

    Vector<Carta> jugador1 = new Vector<Carta>();
    Vector<Carta> jugador2 = new Vector<Carta>();
    Vector<Carta> jugador3 = new Vector<Carta>();
    Vector<Carta> jugador4 = new Vector<Carta>();

    Vector<Carta> baraja = new Vector<Carta>();
    String colores[] = {"verde", "rojo", "azul", "amarillo"};
    String especiales[] = {"saltar", "devolver", "+2"};

    public void llenarListas() {

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 10; j++) {
                Carta carta = new Carta(colores[i], String.valueOf(j));
                baraja.add(carta);
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 1; j < 10; j++) {
                Carta carta = new Carta(colores[i], String.valueOf(j));
                baraja.add(carta);
            }

        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                Carta carta = new Carta(colores[i], especiales[j]);
                baraja.add(carta);
                baraja.add(carta);
            }
        }

        for (int i = 0; i < 4; i++) {
            Carta carta = new Carta("negra", "cambiocolor");
            baraja.add(carta);
        }

        for (int i = 0; i < 4; i++) {
            Carta carta = new Carta("negra", "+4");
            baraja.add(carta);
        }

        Collections.shuffle(baraja);

        for (int i = 0; i < 8; i++) {
            jugador1.add(baraja.get(i));
            baraja.remove(i);
        }
        for (int i = 0; i < 8; i++) {
            jugador4.add(baraja.get(i));
            baraja.remove(i);
        }
        for (int i = 0; i < 8; i++) {
            jugador3.add(baraja.get(i));
            baraja.remove(i);
        }
        for (int i = 0; i < 8; i++) {
            jugador2.add(baraja.get(i));
            baraja.remove(i);
        }

    }

    DefaultListModel modelJugador1 = new DefaultListModel();

    private void poblarListaEnUIconListaJugador1(Vector<Carta> jugador1, DefaultListModel modelList) {
        modelList.clear();
        for (Carta carta : jugador1) {
            modelList.addElement(carta.getNumero() + "  " + carta.getColor());
        }
    }

    private void numeroDeCartas(Vector<Carta> jugador2, Vector<Carta> jugador3, Vector<Carta> jugador4) {
        jLabel8.setText("Tiene " + jugador2.size() + " cartas.");
        jLabel7.setText("Tiene " + jugador3.size() + " cartas.");
        CJ4.setText("Tiene " + jugador4.size() + " cartas.");

        if (jugador2.isEmpty()) {
            JOptionPane.showMessageDialog(CJ4, "El jugador 2 ha ganado");
            System.exit(0);
        }
        if (jugador3.isEmpty()) {
            JOptionPane.showMessageDialog(CJ4, "El jugador 3 ha ganado");
            System.exit(0);
        }
        if (jugador4.isEmpty()) {
            JOptionPane.showMessageDialog(CJ4, "El jugador 4 ha ganado");
            
            System.exit(0);
        }
    }

    private int saltarTurno() {
        if (jLabel9.getText().equals("saltar")) {
            return 2;
        } else {
            return 1;
        }

    }
// LABEL10 COLOR , LABEL9 NUMERO O ESPECIAL

    public int robotAsegurar(Vector<Carta> jugador, int turno) {
        int i = 0;
        for (Carta carta : jugador) {

            if (carta.getColor().equals(jLabel10.getText()) || carta.getNumero().equals(jLabel9.getText()) || carta.getColor().equals("negra")) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public void robotArrastrar(Vector<Carta> jugador, Vector<Carta> baraja) {
        Carta n = new Carta(baraja.get(0).getColor(), baraja.get(0).getNumero());
        jugador.add(n);
        baraja.remove(0);

    }

    public int cartaSuma() {
        if (jLabel9.getText().equals("+2")) {

            return 2;
        }
        if (jLabel9.getText().equals("+4")) {

            return 4;
        }
        return -1;
    }

    public void devolver() {
        if (jLabel11.getText().equals("Normal")) {
            jLabel11.setText("Invertido");
        } else {
            jLabel11.setText("Normal");
        }
    }

    private void turnos(int turn) {

        if (jLabel9.getText().equals("devolver")) {
            devolver();
            jLabel9.setText("Usado");
        }

        if (turn == 0 || turn == 4) {
            if (cartaSuma() == -1) {

                jButton1.setVisible(true);
                jButton3.setVisible(true);
            } else {
                for (int i = 0; i < cartaSuma(); i++) {
                    robotArrastrar(jugador1, baraja);
                }

                poblarListaEnUIconListaJugador1(jugador1, modelJugador1);
                jLabel9.setText("Usado");

                if (jLabel11.getText().equals("Normal")) {
                    turnos(turn + 1);
                } else {
                    turnos(3);
                }

            }

        }

        if (turn == 1) {
            if (cartaSuma() == -1) {

                if (robotAsegurar(jugador2, turn) != -1) {
                    jLabel9.setText(jugador2.get(robotAsegurar(jugador2, turn)).getNumero());
                    jLabel10.setText(jugador2.get(robotAsegurar(jugador2, turn)).getColor());
                    jugador2.remove(robotAsegurar(jugador2, turn));
                    if (jLabel10.getText().equals("negra")) {
                        jLabel10.setText(colores[(int) Math.random() * 3]);
                    }

                } else {
                    JOptionPane.showMessageDialog(CJ4, "Jugador 2 no tiene carta");
                    robotArrastrar(jugador2, baraja);
                }

            } else {
                for (int i = 0; i < cartaSuma(); i++) {
                    robotArrastrar(jugador2, baraja);
                }
                jLabel9.setText("Usado");
            }

            if (jLabel11.getText().equals("Normal")) {
                turnos(turn + saltarTurno());
            } else {
                turnos(0);
            }

        }
        if (turn == 2) {
            if (cartaSuma() == -1) {
                if (robotAsegurar(jugador3, turn) != -1) {
                    jLabel9.setText(jugador3.get(robotAsegurar(jugador3, turn)).getNumero());
                    jLabel10.setText(jugador3.get(robotAsegurar(jugador3, turn)).getColor());
                    jugador3.remove(robotAsegurar(jugador3, turn));

                    if (jLabel10.getText().equals("negra")) {
                        jLabel10.setText(colores[(int) Math.random() * 3]);
                    }

                } else {
                    JOptionPane.showMessageDialog(CJ4, "Jugador 3 no tiene carta");
                    robotArrastrar(jugador3, baraja);
                }
            } else {
                for (int i = 0; i < cartaSuma(); i++) {
                    robotArrastrar(jugador3, baraja);
                }
                jLabel9.setText("Usado");
            }

            if (jLabel11.getText().equals("Normal")) {
                turnos(turn + saltarTurno());
            } else {
                turnos(1);
            }

        }
        if (turn == 3) {
            if (cartaSuma() == -1) {
                if (robotAsegurar(jugador4, turn) != -1) {
                    jLabel9.setText(jugador4.get(robotAsegurar(jugador4, turn)).getNumero());
                    jLabel10.setText(jugador4.get(robotAsegurar(jugador4, turn)).getColor());
                    jugador4.remove(robotAsegurar(jugador4, turn));

                    if (jLabel10.getText().equals("negra")) {
                        jLabel10.setText(colores[(int) Math.random() * 3]);
                    }

                } else {
                    JOptionPane.showMessageDialog(CJ4, "Jugador 4 no tiene carta");
                    robotArrastrar(jugador4, baraja);
                }
            } else {
                for (int i = 0; i < cartaSuma(); i++) {
                    robotArrastrar(jugador4, baraja);
                }
                jLabel9.setText("Usado");
            }

            if (jLabel11.getText().equals("Normal")) {
                turnos(0 + saltarTurno() - 1);
            } else {
                turnos(2);
            }

        }

        numeroDeCartas(jugador2, jugador3, jugador4);

    }

    /**
     * Creates new form Principal
     */
    public Principal() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jList2 = new javax.swing.JList<>();
        jList3 = new javax.swing.JList<>();
        jDialog1 = new javax.swing.JDialog();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        CJ4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jButton2 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel2.setText("Jugador 1");

        jList2.setModel(modelJugador1);

        jList3.setModel(modelJugador1);

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("Jugador 1");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel3.setText("Jugador 2");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel4.setText("Jugador 4");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel5.setText("Jugador 3");

        jButton1.setText("Arrastrar carta");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        CJ4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        CJ4.setText("jLabel6");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setText("jLabel7");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setText("jLabel8");

        jList1.setModel(modelJugador1);
        jScrollPane2.setViewportView(jList1);
        jList1.getAccessibleContext().setAccessibleParent(jList1);

        jButton2.setText("Repartir cartas");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel6.setText("Ingrese el indice donde esta la carta");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel9.setText("Uno");

        jButton3.setText("Seleccionar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Pasar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel10.setText("Juega");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel11.setText("Normal");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jLabel3))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(144, 144, 144)
                                .addComponent(jLabel10)
                                .addGap(33, 33, 33)
                                .addComponent(jLabel9)
                                .addGap(40, 40, 40))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton2)
                                .addGap(110, 110, 110))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                            .addComponent(jTextField2))))
                .addGap(60, 60, 60)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE))
                        .addGap(96, 96, 96))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel4)
                        .addComponent(CJ4, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(339, 339, 339)
                        .addComponent(jLabel5))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(364, 364, 364)
                        .addComponent(jLabel7)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 104, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel10))
                                .addGap(18, 18, 18)
                                .addComponent(jLabel8))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(CJ4)
                                    .addComponent(jButton2))
                                .addGap(12, 12, 12)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(92, 92, 92)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(33, 33, 33))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel11)
                                .addGap(79, 79, 79))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(28, 28, 28))
        );

        jButton1.getAccessibleContext().setAccessibleName("arrastrarCarta");
        CJ4.getAccessibleContext().setAccessibleName("CJ4");
        jLabel7.getAccessibleContext().setAccessibleName("CJ3");
        jLabel8.getAccessibleContext().setAccessibleName("CJ2");
        jButton2.getAccessibleContext().setAccessibleName("Repartircartas");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        Carta n = new Carta(baraja.get(0).getColor(), baraja.get(0).getNumero());
        baraja.remove(0);
        jugador1.add(n);
        poblarListaEnUIconListaJugador1(jugador1, modelJugador1);
        jButton1.setVisible(false);


    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        //BOTON PARA REPARTIR CARTAS

        llenarListas();//LLENA LAS LISTAS DE CADA JUGADOR
        jButton2.setVisible(false);// ESCONDE EL BOTON
        poblarListaEnUIconListaJugador1(jugador1, modelJugador1);//  MUESTRA LA LISTA POR PANTALLA
        numeroDeCartas(jugador2, jugador3, jugador4);// DICE EL NUMERO DE CARTAS DE LOS JUGADORES 2,3,4
        jLabel9.setText(baraja.get(0).getNumero());// CARTA INICIAL
        jLabel10.setText(baraja.get(0).getColor());
        baraja.remove(0);//BORRA CARTA INICIAL


    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TIRAR

        String carta = jTextField2.getText();
        if ((jugador1.get(Integer.parseInt(carta)).getColor()).equals(jLabel10.getText()) || (jugador1.get(Integer.parseInt(carta)).getNumero()).equals(jLabel9.getText())
                || (jugador1.get(Integer.parseInt(carta)).getColor()).equals("negra")) {
            jLabel10.setText(jugador1.get(Integer.parseInt(carta)).getColor());
            jLabel9.setText(jugador1.get(Integer.parseInt(carta)).getNumero());

            jugador1.remove(Integer.parseInt(carta));
            poblarListaEnUIconListaJugador1(jugador1, modelJugador1);

            jButton3.setVisible(false);
            jButton1.setVisible(false);

            if (jLabel10.getText().equals("negra")) {
                String mensaje = JOptionPane.showInputDialog(
                        jPanel1,
                        "Ingrese el color que desea (rojo, amarillo, azul o  verde)",
                        "Elegir color",
                        JOptionPane.QUESTION_MESSAGE);
                jLabel10.setText(mensaje);
            }

        } else {
            JOptionPane.showMessageDialog(jPanel1, "Esta carta no se puede tirar ");
        }
        if (jugador1.isEmpty()) {
            JOptionPane.showMessageDialog(CJ4, "HAS GANADO !!!");
            System.exit(0);
        }

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // PASAR TURNO
        if (jLabel11.getText().equals("Normal")) {
            turnos(saltarTurno());
        } else {
            turnos(3);
        }

    }//GEN-LAST:event_jButton4ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel CJ4;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jList1;
    private javax.swing.JList<String> jList2;
    private javax.swing.JList<String> jList3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
