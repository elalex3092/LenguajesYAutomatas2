/*:-----------------------------------------------------------------------------
 *:                       INSTITUTO TECNOLOGICO DE LA LAGUNA
 *:                     INGENIERIA EN SISTEMAS COMPUTACIONALES
 *:                         LENGUAJES Y AUTOMATAS II           
 *: 
 *:                  SEMESTRE: AGO-DIC/2013     HORA: 12-13 HRS
 *:                                   
 *:               
 *:    # Aplicacion basada en Java Swing con la Interfaz de Usuario del compilador 
 *                 
 *:                           
 *: Archivo       : CompiladorFrame.java
 *: Autor         : Fernando Gil  
 *: Fecha         : 03/Octubre/2013
 *: Compilador    : Java JDK 7
 *: Descripción   : Es aplicacion muestra un frame con la interfaz de usuario  
 *:                 principal del compilador. Provee un menu de opciones para   
 *:                 acceder a las diferentes funciones del compilador, tales como
 *:                 abrir, guardar o imprimir un programa, compilarlo, etc. 
 *:                 Organizado en fichas (tabs) se encuentra el editor para  
 *:                 introducir el programa fuente y vistas del Buffer de Entrada,
 *:                 Tabla de Simbolos y Codigo Intermedio Generado.
 *:           	     
 *: Ult.Modif.    :
 *:  Fecha      Modificó            Modificacion
 *:=============================================================================
 *: 11/Feb/2016 F.Gil               Adaptacion para abrir/guardar programas .raro
 *:-----------------------------------------------------------------------------
 */

package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.print.PrinterException;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

import compilador.ErroresListener;
import compilador.Compilador;
import compilador.Cuadruplo;
import compilador.Cuadruplos;
import compilador.Linea_BE;
import compilador.Linea_TS;
import java.util.ArrayList;

public class CompiladorFrame extends javax.swing.JFrame implements ErroresListener {

    private String registradoA = "";
    private boolean band = false;
    private File archivoSQL;
    private File archivoASM;
    private Font fuentePredeterminado;
    private OpcionesDialog opcionesDlg = new OpcionesDialog(this, true);
    private final String nombreApp = "Compilador";
    private String nombArchSQL = "";
    private String tituloEditor = nombreApp;
    private boolean bandTextoModificado = false;
    // Incluimos un objeto de la clase Compilador
    private Compilador compilador = new Compilador();

//----------------------------------------------------------------------------    
    public CompiladorFrame() {

        initComponents();

        setTitle(tituloEditor);
        bgFuente.add(jrbFuentePredeterminado);
        bgFuente.add(jrbFuenteCastellar);
        bgFuente.add(jrbFuenteRoman);

        bgTamaño.add(jcbTamañoPredeterminado);
        bgTamaño.add(jcbTamaño16);
        bgTamaño.add(jcbTamaño20);

        fuentePredeterminado = jtxaTexto.getFont();

        // Maximizar el Frame a todo el espacio de la pantalla
        Dimension tamPantalla = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds(0, 0, tamPantalla.width, tamPantalla.height - 25);

        // Registramos a la interfaz de usuario como listener de los errores del compilador
        compilador.agregErroresListener(this);
    }
//----------------------------------------------------------------------------

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgFuente = new javax.swing.ButtonGroup();
        bgTamaño = new javax.swing.ButtonGroup();
        jToolBar1 = new javax.swing.JToolBar();
        jbtnArchivoNuevo = new javax.swing.JButton();
        jbtnArchivoAbrir = new javax.swing.JButton();
        jbtnArchivoGuardar = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        jbtnAnalisisLexico = new javax.swing.JButton();
        jbtnAnalisisSintactico = new javax.swing.JButton();
        jbtnAnalisisSemantico = new javax.swing.JButton();
        jbtnGenCodInt = new javax.swing.JButton();
        jbtnGenCodObj = new javax.swing.JButton();
        jbtnGuardarGCO1 = new javax.swing.JButton();
        jSeparator6 = new javax.swing.JToolBar.Separator();
        jbtnCompilarpasoAPaso = new javax.swing.JButton();
        jbtnEjecutar = new javax.swing.JButton();
        jtbpPrincipal = new javax.swing.JTabbedPane();
        jpnlCodigoFuente = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtxaTexto = new javax.swing.JTextArea();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtxaMensajes = new javax.swing.JTextArea();
        jpnlBufferEntrada = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jtblBufferEnt = new javax.swing.JTable();
        jpnlTablaSimbolos = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jtblTablaSimbolos = new javax.swing.JTable();
        jpnlCodigoIntermedio = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtxaCodigoIntermedio = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jtblCuadruplos = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jtxaCodObj = new javax.swing.JTextArea();
        jMenuBar1 = new javax.swing.JMenuBar();
        jmmuArchivo = new javax.swing.JMenu();
        jmniArchivonuevo = new javax.swing.JMenuItem();
        jmniArchivoAbrir = new javax.swing.JMenuItem();
        jmniArchivoGuardar = new javax.swing.JMenuItem();
        jmniArchivoGuardarComo = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jmniArchivoImprimir = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jmniArchivoSalir = new javax.swing.JMenuItem();
        jmmuEditar = new javax.swing.JMenu();
        jmniEditarCortar = new javax.swing.JMenuItem();
        jmniEditarCopiar = new javax.swing.JMenuItem();
        jmniEditarPegar = new javax.swing.JMenuItem();
        jmnuCompilar = new javax.swing.JMenu();
        jmniCompilarLexico = new javax.swing.JMenuItem();
        jmniCompilarSintacticoSemantico = new javax.swing.JMenuItem();
        jmniCompilarSemantico = new javax.swing.JMenuItem();
        jmniCompilarGenerarCodigoIntermedio = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        jmniCompilarEnUnPaso = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        jmniCompilarEjecutar = new javax.swing.JMenuItem();
        jmmuHerramientas = new javax.swing.JMenu();
        jMenu1 = new javax.swing.JMenu();
        jrbFuentePredeterminado = new javax.swing.JRadioButtonMenuItem();
        jrbFuenteCastellar = new javax.swing.JRadioButtonMenuItem();
        jrbFuenteRoman = new javax.swing.JRadioButtonMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jcbTamañoPredeterminado = new javax.swing.JCheckBoxMenuItem();
        jcbTamaño16 = new javax.swing.JCheckBoxMenuItem();
        jcbTamaño20 = new javax.swing.JCheckBoxMenuItem();
        jmniHerramientasOpciones = new javax.swing.JMenuItem();
        jmmuAyuda = new javax.swing.JMenu();
        jmniContenido = new javax.swing.JMenuItem();
        jmniAyudaAcercaDe = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SQL^2012");

        jToolBar1.setRollover(true);

        jbtnArchivoNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Editor/Iconos/Nuevo.png"))); // NOI18N
        jbtnArchivoNuevo.setText("Nuevo");
        jbtnArchivoNuevo.setFocusable(false);
        jbtnArchivoNuevo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbtnArchivoNuevo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbtnArchivoNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnArchivoNuevoActionPerformed(evt);
            }
        });
        jToolBar1.add(jbtnArchivoNuevo);

        jbtnArchivoAbrir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Editor/Iconos/abrir.png"))); // NOI18N
        jbtnArchivoAbrir.setText("Abrir");
        jbtnArchivoAbrir.setAlignmentY(0.35F);
        jbtnArchivoAbrir.setFocusable(false);
        jbtnArchivoAbrir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbtnArchivoAbrir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbtnArchivoAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnArchivoAbrirActionPerformed(evt);
            }
        });
        jToolBar1.add(jbtnArchivoAbrir);

        jbtnArchivoGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Editor/Iconos/Guardar.png"))); // NOI18N
        jbtnArchivoGuardar.setText("Guardar");
        jbtnArchivoGuardar.setAlignmentY(0.35F);
        jbtnArchivoGuardar.setFocusable(false);
        jbtnArchivoGuardar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbtnArchivoGuardar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbtnArchivoGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnArchivoGuardarActionPerformed(evt);
            }
        });
        jToolBar1.add(jbtnArchivoGuardar);
        jToolBar1.add(jSeparator5);

        jbtnAnalisisLexico.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Editor/Iconos/lexico.png"))); // NOI18N
        jbtnAnalisisLexico.setText("A.Lexico");
        jbtnAnalisisLexico.setToolTipText("Analisis de Lexico");
        jbtnAnalisisLexico.setFocusable(false);
        jbtnAnalisisLexico.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbtnAnalisisLexico.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbtnAnalisisLexico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnAnalisisLexicoActionPerformed(evt);
            }
        });
        jToolBar1.add(jbtnAnalisisLexico);

        jbtnAnalisisSintactico.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Editor/Iconos/sintactico.png"))); // NOI18N
        jbtnAnalisisSintactico.setText("A.Sintactico");
        jbtnAnalisisSintactico.setToolTipText("Analisis Sintactico y Semantico");
        jbtnAnalisisSintactico.setFocusable(false);
        jbtnAnalisisSintactico.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbtnAnalisisSintactico.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbtnAnalisisSintactico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnAnalisisSintacticoActionPerformed(evt);
            }
        });
        jToolBar1.add(jbtnAnalisisSintactico);

        jbtnAnalisisSemantico.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Editor/Iconos/sintactico.png"))); // NOI18N
        jbtnAnalisisSemantico.setText("A.Semantico");
        jbtnAnalisisSemantico.setFocusable(false);
        jbtnAnalisisSemantico.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbtnAnalisisSemantico.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbtnAnalisisSemantico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnAnalisisSemanticoActionPerformed(evt);
            }
        });
        jToolBar1.add(jbtnAnalisisSemantico);

        jbtnGenCodInt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Editor/Iconos/codigointermedio.png"))); // NOI18N
        jbtnGenCodInt.setText("G.C.I.");
        jbtnGenCodInt.setToolTipText("Generar Codigo Intermedio");
        jbtnGenCodInt.setFocusable(false);
        jbtnGenCodInt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbtnGenCodInt.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbtnGenCodInt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnGenCodIntActionPerformed(evt);
            }
        });
        jToolBar1.add(jbtnGenCodInt);

        jbtnGenCodObj.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Editor/Iconos/codigointermedio.png"))); // NOI18N
        jbtnGenCodObj.setText("G.C.O.");
        jbtnGenCodObj.setToolTipText("Generar Codigo Intermedio");
        jbtnGenCodObj.setFocusable(false);
        jbtnGenCodObj.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbtnGenCodObj.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbtnGenCodObj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnGenCodObjActionPerformed(evt);
            }
        });
        jToolBar1.add(jbtnGenCodObj);

        jbtnGuardarGCO1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Editor/Iconos/Guardar.png"))); // NOI18N
        jbtnGuardarGCO1.setText("G.C.O.");
        jbtnGuardarGCO1.setToolTipText("Generar Codigo Intermedio");
        jbtnGuardarGCO1.setFocusable(false);
        jbtnGuardarGCO1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbtnGuardarGCO1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbtnGuardarGCO1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnGuardarGCO1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jbtnGuardarGCO1);
        jToolBar1.add(jSeparator6);

        jbtnCompilarpasoAPaso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Editor/Iconos/compilarunpaso.png"))); // NOI18N
        jbtnCompilarpasoAPaso.setText("En un Paso");
        jbtnCompilarpasoAPaso.setToolTipText("Compilar en un paso");
        jbtnCompilarpasoAPaso.setFocusable(false);
        jbtnCompilarpasoAPaso.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbtnCompilarpasoAPaso.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbtnCompilarpasoAPaso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnCompilarpasoAPasoActionPerformed(evt);
            }
        });
        jToolBar1.add(jbtnCompilarpasoAPaso);

        jbtnEjecutar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Editor/Iconos/ejecutar2.png"))); // NOI18N
        jbtnEjecutar.setText("Ejecutar");
        jbtnEjecutar.setToolTipText("Ejecutar el programa");
        jbtnEjecutar.setFocusable(false);
        jbtnEjecutar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbtnEjecutar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbtnEjecutar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnEjecutarActionPerformed(evt);
            }
        });
        jToolBar1.add(jbtnEjecutar);

        jtxaTexto.setColumns(20);
        jtxaTexto.setFont(new java.awt.Font("Courier New", 0, 12)); // NOI18N
        jtxaTexto.setRows(5);
        jtxaTexto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxaTextoKeyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(jtxaTexto);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Mensajes"));

        jtxaMensajes.setColumns(20);
        jtxaMensajes.setEditable(false);
        jtxaMensajes.setForeground(new java.awt.Color(204, 0, 0));
        jtxaMensajes.setLineWrap(true);
        jtxaMensajes.setRows(5);
        jScrollPane2.setViewportView(jtxaMensajes);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 542, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jpnlCodigoFuenteLayout = new javax.swing.GroupLayout(jpnlCodigoFuente);
        jpnlCodigoFuente.setLayout(jpnlCodigoFuenteLayout);
        jpnlCodigoFuenteLayout.setHorizontalGroup(
            jpnlCodigoFuenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlCodigoFuenteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnlCodigoFuenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 566, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jpnlCodigoFuenteLayout.setVerticalGroup(
            jpnlCodigoFuenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlCodigoFuenteLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jtbpPrincipal.addTab("Codigo Fuente", jpnlCodigoFuente);

        jtblBufferEnt.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null}
            },
            new String [] {
                "Token", "Lexema", "Entrada"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(jtblBufferEnt);
        if (jtblBufferEnt.getColumnModel().getColumnCount() > 0) {
            jtblBufferEnt.getColumnModel().getColumn(2).setMinWidth(10);
            jtblBufferEnt.getColumnModel().getColumn(2).setPreferredWidth(15);
        }

        javax.swing.GroupLayout jpnlBufferEntradaLayout = new javax.swing.GroupLayout(jpnlBufferEntrada);
        jpnlBufferEntrada.setLayout(jpnlBufferEntradaLayout);
        jpnlBufferEntradaLayout.setHorizontalGroup(
            jpnlBufferEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlBufferEntradaLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(80, Short.MAX_VALUE))
        );
        jpnlBufferEntradaLayout.setVerticalGroup(
            jpnlBufferEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnlBufferEntradaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                .addContainerGap())
        );

        jtbpPrincipal.addTab("Buffer de Entrada", jpnlBufferEntrada);

        jtblTablaSimbolos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Token", "Lexema", "Tipo", "Ambito"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(jtblTablaSimbolos);

        javax.swing.GroupLayout jpnlTablaSimbolosLayout = new javax.swing.GroupLayout(jpnlTablaSimbolos);
        jpnlTablaSimbolos.setLayout(jpnlTablaSimbolosLayout);
        jpnlTablaSimbolosLayout.setHorizontalGroup(
            jpnlTablaSimbolosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlTablaSimbolosLayout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 481, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(51, Short.MAX_VALUE))
        );
        jpnlTablaSimbolosLayout.setVerticalGroup(
            jpnlTablaSimbolosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnlTablaSimbolosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                .addContainerGap())
        );

        jtbpPrincipal.addTab("Tabla de Simbolos", jpnlTablaSimbolos);

        jtxaCodigoIntermedio.setEditable(false);
        jtxaCodigoIntermedio.setColumns(20);
        jtxaCodigoIntermedio.setFont(new java.awt.Font("Courier New", 0, 12)); // NOI18N
        jtxaCodigoIntermedio.setRows(5);
        jScrollPane3.setViewportView(jtxaCodigoIntermedio);

        javax.swing.GroupLayout jpnlCodigoIntermedioLayout = new javax.swing.GroupLayout(jpnlCodigoIntermedio);
        jpnlCodigoIntermedio.setLayout(jpnlCodigoIntermedioLayout);
        jpnlCodigoIntermedioLayout.setHorizontalGroup(
            jpnlCodigoIntermedioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 594, Short.MAX_VALUE)
            .addGroup(jpnlCodigoIntermedioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jpnlCodigoIntermedioLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 574, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jpnlCodigoIntermedioLayout.setVerticalGroup(
            jpnlCodigoIntermedioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 356, Short.MAX_VALUE)
            .addGroup(jpnlCodigoIntermedioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jpnlCodigoIntermedioLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jtbpPrincipal.addTab("Codigo Intermedio", jpnlCodigoIntermedio);

        jtblCuadruplos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Op", "Arg1", "Arg2", "Resultado", "Lider", "Bloque Basico", "arg1SigUso", "arg2SigUso", "resSigUso"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane6.setViewportView(jtblCuadruplos);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 481, Short.MAX_VALUE)
                .addGap(43, 43, 43))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)
                .addContainerGap())
        );

        jtbpPrincipal.addTab("Cuadruplos", jPanel1);

        jtxaCodObj.setEditable(false);
        jtxaCodObj.setColumns(20);
        jtxaCodObj.setFont(new java.awt.Font("Courier New", 0, 12)); // NOI18N
        jtxaCodObj.setRows(5);
        jScrollPane7.setViewportView(jtxaCodObj);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 586, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 566, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 342, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jtbpPrincipal.addTab("Codigo Objeto", jPanel2);

        jmmuArchivo.setText("Archivo");

        jmniArchivonuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Editor/Iconos/Nuevo.png"))); // NOI18N
        jmniArchivonuevo.setText("Nuevo");
        jmniArchivonuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmniArchivonuevoActionPerformed(evt);
            }
        });
        jmmuArchivo.add(jmniArchivonuevo);

        jmniArchivoAbrir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Editor/Iconos/abrir.png"))); // NOI18N
        jmniArchivoAbrir.setText("Abrir");
        jmniArchivoAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmniArchivoAbrirActionPerformed(evt);
            }
        });
        jmmuArchivo.add(jmniArchivoAbrir);

        jmniArchivoGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Editor/Iconos/Guardar.png"))); // NOI18N
        jmniArchivoGuardar.setText("Guardar");
        jmniArchivoGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmniArchivoGuardarActionPerformed(evt);
            }
        });
        jmmuArchivo.add(jmniArchivoGuardar);

        jmniArchivoGuardarComo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Editor/Iconos/Guardar como.png"))); // NOI18N
        jmniArchivoGuardarComo.setText("Guardar Como");
        jmniArchivoGuardarComo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmniArchivoGuardarComoActionPerformed(evt);
            }
        });
        jmmuArchivo.add(jmniArchivoGuardarComo);
        jmmuArchivo.add(jSeparator1);

        jmniArchivoImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Editor/Iconos/imprimir.png"))); // NOI18N
        jmniArchivoImprimir.setText("Imprimir");
        jmniArchivoImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmniArchivoImprimirActionPerformed(evt);
            }
        });
        jmmuArchivo.add(jmniArchivoImprimir);
        jmmuArchivo.add(jSeparator2);

        jmniArchivoSalir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_MASK));
        jmniArchivoSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Editor/Iconos/salir.png"))); // NOI18N
        jmniArchivoSalir.setText("Salir");
        jmniArchivoSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmniArchivoSalirActionPerformed(evt);
            }
        });
        jmmuArchivo.add(jmniArchivoSalir);

        jMenuBar1.add(jmmuArchivo);

        jmmuEditar.setText("Editar");

        jmniEditarCortar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        jmniEditarCortar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Editor/Iconos/cortar.png"))); // NOI18N
        jmniEditarCortar.setText("Cortar");
        jmniEditarCortar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmniEditarCortarActionPerformed(evt);
            }
        });
        jmmuEditar.add(jmniEditarCortar);

        jmniEditarCopiar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        jmniEditarCopiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Editor/Iconos/copiar.png"))); // NOI18N
        jmniEditarCopiar.setText("Copiar");
        jmniEditarCopiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmniEditarCopiarActionPerformed(evt);
            }
        });
        jmmuEditar.add(jmniEditarCopiar);

        jmniEditarPegar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
        jmniEditarPegar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Editor/Iconos/pegar.png"))); // NOI18N
        jmniEditarPegar.setText("Pegar");
        jmniEditarPegar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmniEditarPegarActionPerformed(evt);
            }
        });
        jmmuEditar.add(jmniEditarPegar);

        jMenuBar1.add(jmmuEditar);

        jmnuCompilar.setText("Compilar");

        jmniCompilarLexico.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Editor/Iconos/lexico.png"))); // NOI18N
        jmniCompilarLexico.setText("Analisis Lexico");
        jmniCompilarLexico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmniCompilarLexicoActionPerformed(evt);
            }
        });
        jmnuCompilar.add(jmniCompilarLexico);

        jmniCompilarSintacticoSemantico.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Editor/Iconos/sintactico.png"))); // NOI18N
        jmniCompilarSintacticoSemantico.setText("Analisis Sintactico-Semantico");
        jmniCompilarSintacticoSemantico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmniCompilarSintacticoSemanticoActionPerformed(evt);
            }
        });
        jmnuCompilar.add(jmniCompilarSintacticoSemantico);

        jmniCompilarSemantico.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Editor/Iconos/sintactico.png"))); // NOI18N
        jmniCompilarSemantico.setText("Analisis Semantico");
        jmniCompilarSemantico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmniCompilarSemanticoActionPerformed(evt);
            }
        });
        jmnuCompilar.add(jmniCompilarSemantico);

        jmniCompilarGenerarCodigoIntermedio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Editor/Iconos/codigointermedio.png"))); // NOI18N
        jmniCompilarGenerarCodigoIntermedio.setText("Generar Codigo Intermedio");
        jmniCompilarGenerarCodigoIntermedio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmniCompilarGenerarCodigoIntermedioActionPerformed(evt);
            }
        });
        jmnuCompilar.add(jmniCompilarGenerarCodigoIntermedio);
        jmnuCompilar.add(jSeparator3);

        jmniCompilarEnUnPaso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Editor/Iconos/compilarunpaso.png"))); // NOI18N
        jmniCompilarEnUnPaso.setText("Compilar en un paso");
        jmniCompilarEnUnPaso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmniCompilarEnUnPasoActionPerformed(evt);
            }
        });
        jmnuCompilar.add(jmniCompilarEnUnPaso);
        jmnuCompilar.add(jSeparator4);

        jmniCompilarEjecutar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Editor/Iconos/ejecutar2.png"))); // NOI18N
        jmniCompilarEjecutar.setText("Ejecutar");
        jmniCompilarEjecutar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmniCompilarEjecutarActionPerformed(evt);
            }
        });
        jmnuCompilar.add(jmniCompilarEjecutar);

        jMenuBar1.add(jmnuCompilar);

        jmmuHerramientas.setText("Herramientas");

        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Editor/Iconos/Fuente.png"))); // NOI18N
        jMenu1.setText("Fuente");

        jrbFuentePredeterminado.setSelected(true);
        jrbFuentePredeterminado.setText("Predeterminado");
        jrbFuentePredeterminado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbFuentePredeterminadoActionPerformed(evt);
            }
        });
        jMenu1.add(jrbFuentePredeterminado);

        jrbFuenteCastellar.setSelected(true);
        jrbFuenteCastellar.setText("CASTELLAR");
        jrbFuenteCastellar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbFuenteCastellarActionPerformed(evt);
            }
        });
        jMenu1.add(jrbFuenteCastellar);

        jrbFuenteRoman.setSelected(true);
        jrbFuenteRoman.setText("Times New Roman");
        jrbFuenteRoman.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbFuenteRomanActionPerformed(evt);
            }
        });
        jMenu1.add(jrbFuenteRoman);

        jmmuHerramientas.add(jMenu1);

        jMenu2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Editor/Iconos/tamaño.png"))); // NOI18N
        jMenu2.setText("Tamaño");

        jcbTamañoPredeterminado.setSelected(true);
        jcbTamañoPredeterminado.setText("Predeterminado");
        jcbTamañoPredeterminado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbTamañoPredeterminadoActionPerformed(evt);
            }
        });
        jMenu2.add(jcbTamañoPredeterminado);

        jcbTamaño16.setSelected(true);
        jcbTamaño16.setText("16");
        jcbTamaño16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbTamaño16ActionPerformed(evt);
            }
        });
        jMenu2.add(jcbTamaño16);

        jcbTamaño20.setSelected(true);
        jcbTamaño20.setText("20");
        jcbTamaño20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbTamaño20ActionPerformed(evt);
            }
        });
        jMenu2.add(jcbTamaño20);

        jmmuHerramientas.add(jMenu2);

        jmniHerramientasOpciones.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Editor/Iconos/opciones.png"))); // NOI18N
        jmniHerramientasOpciones.setText("Opciones");
        jmniHerramientasOpciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmniHerramientasOpcionesActionPerformed(evt);
            }
        });
        jmmuHerramientas.add(jmniHerramientasOpciones);

        jMenuBar1.add(jmmuHerramientas);

        jmmuAyuda.setText("Ayuda");

        jmniContenido.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Editor/Iconos/registrar.png"))); // NOI18N
        jmniContenido.setText("Contenido");
        jmniContenido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmniContenidoActionPerformed(evt);
            }
        });
        jmmuAyuda.add(jmniContenido);

        jmniAyudaAcercaDe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Editor/Iconos/acerca.png"))); // NOI18N
        jmniAyudaAcercaDe.setText("Acerca de");
        jmniAyudaAcercaDe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmniAyudaAcercaDeActionPerformed(evt);
            }
        });
        jmmuAyuda.add(jmniAyudaAcercaDe);

        jMenuBar1.add(jmmuAyuda);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtbpPrincipal)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jtbpPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 381, Short.MAX_VALUE)
                .addContainerGap())
        );

        setSize(new java.awt.Dimension(617, 507));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public void EstablecerOpciones() {

        FileReader fr = null;

        try {
            fr = new FileReader("Opciones.txt");
            BufferedReader bf = new BufferedReader(fr);
            String aux2 = " ";
            String var = " ";

            while ((var = bf.readLine()) != null) {
                aux2 += var + "\n\r";

                try {

                    String var_temp = var.substring(0, 10).trim().toLowerCase();


                    if (var_temp.compareTo("brillo") == 0) {
                        int v = Integer.parseInt(var.substring(12, var.length() - 1).trim());
                        opcionesDlg.Brillo(v);
                    } else if (var_temp.compareTo("fondo") == 0) {
                        if (var.substring(12, var.length() - 1).trim().equals("true"));
                        opcionesDlg.Fondo();
                    } else if (var_temp.compareTo("idioma") == 0) {
                        int ind = Integer.parseInt(var.substring(12, var.length() - 1).trim());
                        opcionesDlg.Idioma(ind);
                    } else if (var_temp.compareTo("informar") == 0) {
                        if (var.substring(12, var.length() - 1).trim().equals("true")) {
                            opcionesDlg.Informar();
                        }
                    } else if (var_temp.compareTo("todo") == 0) {
                        if (var.substring(12, var.length() - 1).trim().equals("true")) {
                            opcionesDlg.Todo();
                        } else {
                            opcionesDlg.soloTexto();
                        }
                    } else if (var_temp.compareTo("contraste") == 0) {
                        int c = Integer.parseInt(var.substring(12, var.length() - 1).trim());
                        opcionesDlg.Contraste(c);
                    }
                    
                } catch (Exception ex) {
                }
            }
        } catch (Exception ex) {
        } finally {
            if (fr != null) {
                try {
                    fr.close();
                } catch (Exception ex) {
                }
            }
        }
    }
//----------------------------------------------------------------------------       

    public void Cambiar_Fondo(Color c) {

        if (c == Color.BLUE) {
            jtxaTexto.setBackground(c);
            jtxaTexto.setForeground(Color.YELLOW);
        } else {
            jtxaTexto.setBackground(c);
            jtxaTexto.setForeground(Color.BLACK);
        }
    }
//----------------------------------------------------------------------------     
    private void jmniArchivoSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmniArchivoSalirActionPerformed

        int boton = JOptionPane.showConfirmDialog(this, "Desea salir?", nombreApp, JOptionPane.YES_NO_OPTION);

        if (boton == JOptionPane.YES_NO_OPTION) {
            System.exit(0);
        }
    }//GEN-LAST:event_jmniArchivoSalirActionPerformed
//----------------------------------------------------------------------------
    private void jmniArchivonuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmniArchivonuevoActionPerformed
        if (bandTextoModificado) {
            int resp = JOptionPane.showConfirmDialog(
                    this,
                    "Desea guardar los cambios?",
                    "Nuevo",
                    JOptionPane.YES_NO_OPTION);
            if (resp == JOptionPane.YES_OPTION) {
                jmniArchivoGuardarComoActionPerformed(evt);
            } else {
                archivoNuevo();
            }
        } else {
            archivoNuevo();
        }
    }//GEN-LAST:event_jmniArchivonuevoActionPerformed
//----------------------------------------------------------------------------

    private void archivoNuevo () {
        jtxaTexto.setText     ( "" );
        jtxaTexto.requestFocus();
        band        = false;
        archivoSQL  = null;
        nombArchSQL = "";
        bandTextoModificado = false;
        actualizarTituloEditor();
    }
//----------------------------------------------------------------------------
    private void jbtnArchivoNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnArchivoNuevoActionPerformed
        jmniArchivonuevoActionPerformed(evt);
    }//GEN-LAST:event_jbtnArchivoNuevoActionPerformed
//----------------------------------------------------------------------------
    private void jmniArchivoImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmniArchivoImprimirActionPerformed

        try {
            jtxaTexto.print();
        } catch (PrinterException ex) {
            JOptionPane.showMessageDialog(this, "Error al intentar imprimir");
        }
    }//GEN-LAST:event_jmniArchivoImprimirActionPerformed
//----------------------------------------------------------------------------
    private void jmniEditarCopiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmniEditarCopiarActionPerformed
        jtxaTexto.copy();
    }//GEN-LAST:event_jmniEditarCopiarActionPerformed
//----------------------------------------------------------------------------
    private void jmniEditarPegarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmniEditarPegarActionPerformed
        jtxaTexto.paste();
    }//GEN-LAST:event_jmniEditarPegarActionPerformed
//----------------------------------------------------------------------------
    private void jmniContenidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmniContenidoActionPerformed

        try {
            Process proceso = Runtime.getRuntime().exec(
                    "C:\\Program Files\\Adobe\\Reader 10.0\\Reader\\AcroRd32.exe EspecificacionesDelCompilador.pdf");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog ( 
                    this,
                    "No se puede ejecutar Acrobat Reader.",
                    "Error", 
                    JOptionPane.ERROR_MESSAGE );
        }

    }//GEN-LAST:event_jmniContenidoActionPerformed
//----------------------------------------------------------------------------

    private void jmniAyudaAcercaDeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmniAyudaAcercaDeActionPerformed
        AcercadeDialog acercaDe = new AcercadeDialog(this, true);
        acercaDe.setVisible(true);
    }//GEN-LAST:event_jmniAyudaAcercaDeActionPerformed
//----------------------------------------------------------------------------
    private void jmniArchivoAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmniArchivoAbrirActionPerformed

        File archivo;
        String texto = "";
        JFileChooser jfc = new JFileChooser();
        jfc.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Archivos de programa Raros (*.raro)", "Raro"));
        if (jfc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {

            archivo = jfc.getSelectedFile();
            band = true;
            archivoSQL = archivo;
            FileReader fr = null;
            BufferedReader leer = null;

            try {

                fr = new FileReader(archivo);
                leer = new BufferedReader(fr);
                String s;
                while ((s = leer.readLine()) != null) {
                    texto += s + "\n";
                }

                jtxaTexto.setText(texto);
                actualizarTituloEditor();
                bandTextoModificado = false;                    
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            } finally {
                if (fr != null) {
                    try {
                        fr.close();
                    } catch (IOException ex) {
                    }
                }
            }
        }
    }//GEN-LAST:event_jmniArchivoAbrirActionPerformed
//----------------------------------------------------------------------------
    private void jbtnArchivoAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnArchivoAbrirActionPerformed
        jmniArchivoAbrirActionPerformed(evt);
    }//GEN-LAST:event_jbtnArchivoAbrirActionPerformed
//----------------------------------------------------------------------------
    private void jmniArchivoGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmniArchivoGuardarActionPerformed

        if (band == false) {
            File archivo;
            JFileChooser jfc = new JFileChooser();
            jfc.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                    "Archivos de programa Raros (*.raro)", "Raro"));

            if (jfc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {

                archivo = jfc.getSelectedFile();
                band = true;
                archivoSQL = archivo;

                FileWriter fw = null;

                try {
                    fw = new FileWriter(archivo + ".raro", false);
                    String s = jtxaTexto.getText();

                    fw.write(s);
                    actualizarTituloEditor();
                    bandTextoModificado = false;                    
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                } finally {
                    if (fw != null) {
                        try {
                            fw.close();
                        } catch (Exception ex) {
                        }
                    }
                }
            }
        } else if (band == true) {

            FileWriter fw = null;
            BufferedWriter bw = null;
            PrintWriter pw = null;
            try {
                fw = new FileWriter(archivoSQL, false);
                bw = new BufferedWriter(fw);
                pw = new PrintWriter(bw);


                pw.write(jtxaTexto.getText());
                bandTextoModificado = false;                    
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            } finally {
                if (fw != null) {
                    try {
                        pw.close();
                        fw.close();
                    } catch (Exception ex) {
                    }
                }
            }
        }
    }//GEN-LAST:event_jmniArchivoGuardarActionPerformed
//----------------------------------------------------------------------------

    private void jmniArchivoGuardarComoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmniArchivoGuardarComoActionPerformed

        File archivo;
        JFileChooser jfc = new JFileChooser();
        jfc.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Archivos de programa Raros (*.raro)", "Raro"));


        if (jfc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {

            archivo = jfc.getSelectedFile();
            archivoSQL = archivo;

            FileWriter fw = null;
            try {
                fw = new FileWriter(archivo + ".raro", false);
                String s = jtxaTexto.getText();
                fw.write(s);
                actualizarTituloEditor();
                bandTextoModificado = false;                    
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            } finally {
                if (fw != null) {
                    try {
                        fw.close();
                    } catch (Exception ex) {
                    }
                }
            }
        }
    }//GEN-LAST:event_jmniArchivoGuardarComoActionPerformed
//----------------------------------------------------------------------------
    private void jbtnArchivoGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnArchivoGuardarActionPerformed

        jmniArchivoGuardarActionPerformed(evt);

    }//GEN-LAST:event_jbtnArchivoGuardarActionPerformed
//----------------------------------------------------------------------------
    private void jrbFuentePredeterminadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbFuentePredeterminadoActionPerformed

        Font actual = jtxaTexto.getFont();
        Font f = new Font("Arial",
                actual.getStyle(),
                actual.getSize());

        jtxaTexto.setFont(f);

    }//GEN-LAST:event_jrbFuentePredeterminadoActionPerformed
//----------------------------------------------------------------------------
    private void jrbFuenteCastellarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbFuenteCastellarActionPerformed

        Font actual = jtxaTexto.getFont();
        Font f = new Font("CASTELLAR",
                actual.getStyle(),
                actual.getSize());

        jtxaTexto.setFont(f);

    }//GEN-LAST:event_jrbFuenteCastellarActionPerformed
//----------------------------------------------------------------------------
    private void jrbFuenteRomanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbFuenteRomanActionPerformed

        Font actual = jtxaTexto.getFont();
        Font f = new Font("Times New Roman",
                actual.getStyle(),
                actual.getSize());

        jtxaTexto.setFont(f);

    }//GEN-LAST:event_jrbFuenteRomanActionPerformed
//----------------------------------------------------------------------------
    private void jcbTamañoPredeterminadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbTamañoPredeterminadoActionPerformed

        Font actual = jtxaTexto.getFont();
        Font f = new Font(actual.getFontName(),
                actual.getStyle(),
                fuentePredeterminado.getSize());

        jtxaTexto.setFont(f);

    }//GEN-LAST:event_jcbTamañoPredeterminadoActionPerformed
//----------------------------------------------------------------------------
    private void jcbTamaño16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbTamaño16ActionPerformed

        Font actual = jtxaTexto.getFont();
        Font f = new Font(actual.getFontName(),
                actual.getStyle(),
                16);

        jtxaTexto.setFont(f);
    }//GEN-LAST:event_jcbTamaño16ActionPerformed
//----------------------------------------------------------------------------
    private void jcbTamaño20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbTamaño20ActionPerformed

        Font actual = jtxaTexto.getFont();
        Font f = new Font(actual.getFontName(),
                actual.getStyle(),
                20);

        jtxaTexto.setFont(f);
    }//GEN-LAST:event_jcbTamaño20ActionPerformed
//----------------------------------------------------------------------------
    private void jmniEditarCortarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmniEditarCortarActionPerformed

         jtxaTexto.cut();     }//GEN-LAST:event_jmniEditarCortarActionPerformed
//----------------------------------------------------------------------------

    private void jmniHerramientasOpcionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmniHerramientasOpcionesActionPerformed
        EstablecerOpciones();
        opcionesDlg.setVisible(true);

    }//GEN-LAST:event_jmniHerramientasOpcionesActionPerformed
//----------------------------------------------------------------------------

    private void jmniCompilarLexicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmniCompilarLexicoActionPerformed
        jtxaMensajes.setText("ANALISIS DE LEXICO INICIADO....\n");
        compilador.analizarLexico(jtxaTexto.getText());
        actualizarGUIBufferEntrada();
        actualizarGUITablaSimbolos();
        jtxaMensajes.append("*******Analisis de Lexico terminado*******\n");
        jtxaMensajes.append(compilador.getTotErrores(Compilador.ERR_LEXICO)
                + "  error(es) encontrado(s).\n\n");
    }//GEN-LAST:event_jmniCompilarLexicoActionPerformed

//----------------------------------------------------------------------------
    private void jbtnAnalisisLexicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnAnalisisLexicoActionPerformed
        jmniCompilarLexicoActionPerformed(evt);
    }//GEN-LAST:event_jbtnAnalisisLexicoActionPerformed
//----------------------------------------------------------------------------

    private void jmniCompilarSintacticoSemanticoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmniCompilarSintacticoSemanticoActionPerformed
        jmniCompilarLexicoActionPerformed(evt);
        jtxaMensajes.append("ANALISIS SINTACTICO INICIADO....\n");
        compilador.analizarSintaxis();
        actualizarGUITablaSimbolos();
        jtxaMensajes.append("*******Analisis Sintactico terminado*******\n");
        jtxaMensajes.append(compilador.getTotErrores(Compilador.ERR_SINTACTICO)
                + "  error(es) encontrado(s).\n\n");

    }//GEN-LAST:event_jmniCompilarSintacticoSemanticoActionPerformed
//----------------------------------------------------------------------------

    private void jbtnAnalisisSintacticoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnAnalisisSintacticoActionPerformed
        jmniCompilarSintacticoSemanticoActionPerformed(evt);
    }//GEN-LAST:event_jbtnAnalisisSintacticoActionPerformed
//----------------------------------------------------------------------------

    private void jtxaTextoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxaTextoKeyTyped
        bandTextoModificado = true;
    }//GEN-LAST:event_jtxaTextoKeyTyped
//----------------------------------------------------------------------------

    private void jmniCompilarGenerarCodigoIntermedioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmniCompilarGenerarCodigoIntermedioActionPerformed
        jmniCompilarLexicoActionPerformed(evt);
        jmniCompilarSemanticoActionPerformed(evt);
        
        jtxaMensajes.append("GENERACION DE CODIGO INTERMEDIO INICIADO....\n");
        jtxaCodigoIntermedio.setText ( "" );
        jtxaCodObj.setText(null);
        compilador.generarCodigoInt();
        actualizarGUICuadruplos();
        actualizarGUITablaSimbolos();
        jtxaMensajes.append("*******Codigo Intermedio terminado*******\n");
        jtxaMensajes.append(compilador.getTotErrores ( Compilador.ERR_CODINT )
                + "  error(es) encontrado(s).\n\n");
    }//GEN-LAST:event_jmniCompilarGenerarCodigoIntermedioActionPerformed
//----------------------------------------------------------------------------
       private void jmniCompilarGenerarCodigoObjetoActionPerformed(java.awt.event.ActionEvent evt) {                                                                    
        jmniCompilarGenerarCodigoIntermedioActionPerformed(evt);
        jtxaMensajes.append("GENERACION DE CODIGO OBJETO INICIADO....\n");
        jtxaCodObj.setText(null);
        compilador.generarCodigoObj();
        actualizarGUICuadruplos();
        actualizarGUITablaSimbolos();
        jtxaMensajes.append("*******Codigo Objeto terminado*******\n");
        jtxaMensajes.append(compilador.getTotErrores ( Compilador.ERR_CODOBJ )
                + "  error(es) encontrado(s).\n\n");
    } 

    private void jbtnGenCodIntActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnGenCodIntActionPerformed
        jmniCompilarGenerarCodigoIntermedioActionPerformed ( evt ); 
    }//GEN-LAST:event_jbtnGenCodIntActionPerformed
//----------------------------------------------------------------------------
 
    private void jmniCompilarEnUnPasoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmniCompilarEnUnPasoActionPerformed
        jmniCompilarGenerarCodigoIntermedioActionPerformed ( evt );
    }//GEN-LAST:event_jmniCompilarEnUnPasoActionPerformed
//----------------------------------------------------------------------------

    private void jbtnCompilarpasoAPasoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnCompilarpasoAPasoActionPerformed
        jmniCompilarEnUnPasoActionPerformed ( evt );
    }//GEN-LAST:event_jbtnCompilarpasoAPasoActionPerformed
//----------------------------------------------------------------------------

    private void jmniCompilarEjecutarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmniCompilarEjecutarActionPerformed
        jmniCompilarEnUnPasoActionPerformed ( evt );
        // Suma el total de errores
        int totErrores = compilador.getTotErrores ( Compilador.ERR_LEXICO    ) +  
                         compilador.getTotErrores ( Compilador.ERR_SINTACTICO) +
                         compilador.getTotErrores ( Compilador.ERR_CODINT    );
        // Verificar si hubo errores no se ejecuta
        if ( totErrores > 0 ) 
            JOptionPane.showMessageDialog ( 
                    this,
                    "Programa tiene errores, no se puede ejecutar.",
                    "Error", 
                    JOptionPane.ERROR_MESSAGE );
        else {
            String programaFox = jtxaCodigoIntermedio.getText ();
            try {
              // Escribir el codigo intermedio en un archivo .PRG
              FileWriter fw = new FileWriter ( "FOX2.0\\codigofx.prg" );
              fw.write ( programaFox );
              fw.close ();
              // Ejecutar
              JOptionPane.showMessageDialog ( 
                    this,
                    "Ejecute el archivo  FOX.BAT  para correr el programa.",
                    "Ejecutar", 
                    JOptionPane.INFORMATION_MESSAGE );
            } catch ( Exception ex )   {
              JOptionPane.showMessageDialog ( 
                    this,
                    "Error al crear o ejecutar codigofx.prg\n\n" + ex,
                    "Error", 
                    JOptionPane.ERROR_MESSAGE );
            }
        }
    }//GEN-LAST:event_jmniCompilarEjecutarActionPerformed
//----------------------------------------------------------------------------

    private void jbtnEjecutarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnEjecutarActionPerformed
        jmniCompilarEjecutarActionPerformed ( evt );
    }//GEN-LAST:event_jbtnEjecutarActionPerformed

    private void jbtnAnalisisSemanticoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnAnalisisSemanticoActionPerformed
        // TODO add your handling code here:
        jmniCompilarSemanticoActionPerformed ( evt );
    }//GEN-LAST:event_jbtnAnalisisSemanticoActionPerformed

    private void jmniCompilarSemanticoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmniCompilarSemanticoActionPerformed
        // TODO add your handling code here:
        jmniCompilarLexicoActionPerformed(evt);
        jmniCompilarSintacticoSemanticoActionPerformed ( evt );
        jtxaMensajes.append("ANALISIS SEMANTICO INICIADO....\n");
        compilador.analizarSemantica();
        actualizarGUITablaSimbolos();
        jtxaMensajes.append("*******Analisis Semantico terminado*******\n");
        jtxaMensajes.append(compilador.getTotErrores(Compilador.ERR_SEMANTICO)
                + "  error(es) encontrado(s).\n\n");
        jtxaMensajes.append(compilador.getTotErrores(Compilador.WARNING_SEMANT)
                + "  advertencia(s) encontrada(s).\n\n" );
    }//GEN-LAST:event_jmniCompilarSemanticoActionPerformed

    private void jbtnGenCodObjActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnGenCodObjActionPerformed
        
      jmniCompilarGenerarCodigoObjetoActionPerformed ( evt );
     
    }//GEN-LAST:event_jbtnGenCodObjActionPerformed

    private void jbtnGuardarGCO1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnGuardarGCO1ActionPerformed
        if (band == false) {
            File archivo;
            JFileChooser jfc = new JFileChooser();
            jfc.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Archivos ASM (*.asm)", "asm"));

        if (jfc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {

            archivo = jfc.getSelectedFile();
            band = true;
            archivoASM = archivo;

            FileWriter fw = null;

            try {
                fw = new FileWriter(archivo + ".asm", false);
                String s = jtxaCodObj.getText();

                fw.write(s);
                actualizarTituloEditor();
                bandTextoModificado = false;
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            } finally {
                if (fw != null) {
                    try {
                        fw.close();
                    } catch (Exception ex) {
                    }
                }
            }
        }
        } else if (band == true) {

            FileWriter fw = null;
            BufferedWriter bw = null;
            PrintWriter pw = null;
            try {
                fw = new FileWriter(archivoASM, false);
                bw = new BufferedWriter(fw);
                pw = new PrintWriter(bw);

                pw.write(jtxaCodObj.getText());
                bandTextoModificado = false;
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            } finally {
                if (fw != null) {
                    try {
                        pw.close();
                        fw.close();
                    } catch (Exception ex) {
                    }
                }
            }
        }
    }//GEN-LAST:event_jbtnGuardarGCO1ActionPerformed

//----------------------------------------------------------------------------
    public static void main(String args[]) {
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CompiladorFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CompiladorFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CompiladorFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CompiladorFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>


        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {

                new CompiladorFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgFuente;
    private javax.swing.ButtonGroup bgTamaño;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JToolBar.Separator jSeparator6;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JButton jbtnAnalisisLexico;
    private javax.swing.JButton jbtnAnalisisSemantico;
    private javax.swing.JButton jbtnAnalisisSintactico;
    private javax.swing.JButton jbtnArchivoAbrir;
    private javax.swing.JButton jbtnArchivoGuardar;
    private javax.swing.JButton jbtnArchivoNuevo;
    private javax.swing.JButton jbtnCompilarpasoAPaso;
    private javax.swing.JButton jbtnEjecutar;
    private javax.swing.JButton jbtnGenCodInt;
    private javax.swing.JButton jbtnGenCodObj;
    private javax.swing.JButton jbtnGuardarGCO1;
    private javax.swing.JCheckBoxMenuItem jcbTamaño16;
    private javax.swing.JCheckBoxMenuItem jcbTamaño20;
    private javax.swing.JCheckBoxMenuItem jcbTamañoPredeterminado;
    private javax.swing.JMenu jmmuArchivo;
    private javax.swing.JMenu jmmuAyuda;
    private javax.swing.JMenu jmmuEditar;
    private javax.swing.JMenu jmmuHerramientas;
    private javax.swing.JMenuItem jmniArchivoAbrir;
    private javax.swing.JMenuItem jmniArchivoGuardar;
    private javax.swing.JMenuItem jmniArchivoGuardarComo;
    private javax.swing.JMenuItem jmniArchivoImprimir;
    private javax.swing.JMenuItem jmniArchivoSalir;
    private javax.swing.JMenuItem jmniArchivonuevo;
    private javax.swing.JMenuItem jmniAyudaAcercaDe;
    private javax.swing.JMenuItem jmniCompilarEjecutar;
    private javax.swing.JMenuItem jmniCompilarEnUnPaso;
    private javax.swing.JMenuItem jmniCompilarGenerarCodigoIntermedio;
    private javax.swing.JMenuItem jmniCompilarLexico;
    private javax.swing.JMenuItem jmniCompilarSemantico;
    private javax.swing.JMenuItem jmniCompilarSintacticoSemantico;
    private javax.swing.JMenuItem jmniContenido;
    private javax.swing.JMenuItem jmniEditarCopiar;
    private javax.swing.JMenuItem jmniEditarCortar;
    private javax.swing.JMenuItem jmniEditarPegar;
    private javax.swing.JMenuItem jmniHerramientasOpciones;
    private javax.swing.JMenu jmnuCompilar;
    private javax.swing.JPanel jpnlBufferEntrada;
    private javax.swing.JPanel jpnlCodigoFuente;
    private javax.swing.JPanel jpnlCodigoIntermedio;
    private javax.swing.JPanel jpnlTablaSimbolos;
    private javax.swing.JRadioButtonMenuItem jrbFuenteCastellar;
    private javax.swing.JRadioButtonMenuItem jrbFuentePredeterminado;
    private javax.swing.JRadioButtonMenuItem jrbFuenteRoman;
    private javax.swing.JTable jtblBufferEnt;
    private javax.swing.JTable jtblCuadruplos;
    private javax.swing.JTable jtblTablaSimbolos;
    private javax.swing.JTabbedPane jtbpPrincipal;
    private javax.swing.JTextArea jtxaCodObj;
    private javax.swing.JTextArea jtxaCodigoIntermedio;
    private javax.swing.JTextArea jtxaMensajes;
    private javax.swing.JTextArea jtxaTexto;
    // End of variables declaration//GEN-END:variables

    //--------------------------------------------------------------------------
    @Override
    public void mostrarErrores(String error) {

        jtxaMensajes.append(error + "\n");
    }

    //--------------------------------------------------------------------------
    private void actualizarGUIBufferEntrada() {
        String[] nombresCol = {"Complex", "Lexema", "Entrada"};
        Linea_BE[] buffEnt = compilador.getBufferEntrada();
        Object[][] datos = new Object[buffEnt.length][3];

        for (int i = 0; i < buffEnt.length; i++) {
            datos[i][0] = buffEnt[i].getComplex();
            datos[i][1] = buffEnt[i].getLexema();
            datos[i][2] = new Integer(buffEnt[i].getEntrada());
        }


        // Cambiamos el modelo actual del JTable del Buffer de Entrada por uno 
        // nuevo con los datos de arriba. Se perderan las opciones de celdas 
        // editables/ajustables, etc.
        DefaultTableModel modelo = new DefaultTableModel(datos, nombresCol);
        jtblBufferEnt.setModel(
                new DefaultTableModel(datos, nombresCol) {

                    boolean[] canEdit = new boolean[]{
                        false, false, false
                    };

                    @Override
                    public boolean isCellEditable(int rowIndex, int columnIndex) {
                        return canEdit[ columnIndex];
                    }
                });
    }

    //--------------------------------------------------------------------------
    private void actualizarGUITablaSimbolos() {
        String[] nombresCol = {"#", "Complex", "Lexema", "Tipo","SigUso"};
        Linea_TS[] tablaSimb = compilador.getTablaSimbolos();
        Object[][] datos = new Object[tablaSimb.length][5];

        for (int i = 0; i < tablaSimb.length; i++) {
            datos[i][0] = new Integer(i);
            datos[i][1] = tablaSimb[i].getComplex();
            datos[i][2] = tablaSimb[i].getLexema();
            datos[i][3] = tablaSimb[i].getTipo();
            datos[i][4] = tablaSimb[i].getSigUso();
            
        }

        
        

        // Cambiamos el modelo actual del JTable de la Tabla de Simbolos por uno 
        // nuevo con los datos de arriba. Se perderan las opciones de celdas 
        // editables/ajustables, etc.
        DefaultTableModel modelo = new DefaultTableModel(datos, nombresCol);
        jtblTablaSimbolos.setModel(
                new DefaultTableModel(datos, nombresCol)/* {
                
                    boolean[] canEdit = new boolean[]{
                        false, false, false
                    };

                    @Override
                    public boolean isCellEditable(int rowIndex, int columnIndex) {
                        return canEdit[ columnIndex ];
                    }
                }*/);
    }
    
    //--------------------------------------------------------------------------
   
    private void actualizarGUICuadruplos() {
         String[] nombresCol = { "#", "Op", "Arg1", "Arg1SigUso", "Arg2", "Arg2SigUso",
                                "Resultado", "ResSigUso", "Lider",
                                "Bloque" };
        ArrayList<Cuadruplo> cuadruplos = compilador.getCuadruplos ();
        Object[][] datos = new Object[cuadruplos.size()][10];

        for ( int i = 0; i < cuadruplos.size (); i++ ) {
            datos[i][0] = i+1;
            datos[i][1] = cuadruplos.get ( i ).op;
            datos[i][2] = cuadruplos.get ( i ).arg1;
            if ( cuadruplos.get(i).arg1SigUso == 0 )
                datos[i][3] = "X";
            else
                datos[i][3] = cuadruplos.get ( i ).arg1SigUso;
            datos[i][4] = cuadruplos.get ( i ).arg2;
             if ( cuadruplos.get(i).arg2SigUso == 0 )
                datos[i][5] = "X";
            else            
                datos[i][5] = cuadruplos.get ( i ).arg2SigUso;
            datos[i][6] = cuadruplos.get ( i ).resultado;
             if ( cuadruplos.get(i).resSigUso == 0 )
                datos[i][7] = "X";
            else            
            datos[i][7] = cuadruplos.get ( i ).resSigUso;
            datos[i][8] = cuadruplos.get ( i ).lider;
            datos[i][9] = cuadruplos.get ( i ).bloqueBasico;
        }
        // Cambiamos el modelo actual del JTable de la Tabla de Simbolos por uno 
        // nuevo con los datos de arriba. Se perderan las opciones de celdas 
        // editables/ajustables, etc.
        DefaultTableModel modelo = new DefaultTableModel(datos, nombresCol);
        jtblCuadruplos.setModel(
                new DefaultTableModel(datos, nombresCol)/* {
                
                    boolean[] canEdit = new boolean[]{
                        false, false, false
                    };

                    @Override
                    public boolean isCellEditable(int rowIndex, int columnIndex) {
                        return canEdit[ columnIndex ];
                    }
                }*/);
    }

    //--------------------------------------------------------------------------
    private void actualizarTituloEditor() {
        if (archivoSQL != null) {
            nombArchSQL = archivoSQL.getName();
        }

        if (nombArchSQL != null) {
            if (nombArchSQL.equals("")) {
                tituloEditor = nombreApp;
            } else {
                tituloEditor = nombreApp + " - " + nombArchSQL;
            }
            setTitle(tituloEditor);
        }
    }
    //--------------------------------------------------------------------------
 
    @Override
    public void mostrarCodInt ( String codint ) {
      
        jtxaCodigoIntermedio.append ( codint + "\n" );
    }

    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // Mostrara los warnings producidos por el semantico
    //
    @Override
    public void mostrarWarning ( String warning )
    {
        jtxaMensajes.append( warning + "\n");
    }
    //
    //--------------------------------------------------------------------------
    
     //--------------------------------------------------------------------------
    // Mostrara los warnings producidos por el semantico
    //
    @Override
    public void mostrarCodObj ( String codObj )
    {
        jtxaCodObj.append( codObj + "\n");
    }
    //
    //--------------------------------------------------------------------------
    
}
