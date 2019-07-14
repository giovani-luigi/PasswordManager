package myid.view;

import myid.controllers.MainViewController;
import myid.model.Profile;
import myid.storage.IStoreProfiles;

public class MainView extends javax.swing.JFrame {

    MainViewController controller;
    
    private void onViewLoaded(){
        updateAllView();
    }
    
    public MainView(IStoreProfiles storage) {
        initComponents();
        this.controller = new MainViewController(storage);
    }
    
    private void updateAllView(){
        // update list of available profiles
        profilesList.setModel(controller.getAllProfileAliases());
        // update displayed data of current profile
        updateCurrentProfileView();
    }
    
    /*
    * update info of selected profile
    **/
    private void updateCurrentProfileView(){
        
        Profile profile = controller.getCurrentProfile();
        if (profile != null){
            textAlias.setText(profile.getAlias());
            textPassword.setText(profile.getPwd());
            textUrl.setText(profile.getUrl());
            textUser.setText(profile.getUser());
        }else{
            textAlias.setText("");
            textPassword.setText("");
            textUrl.setText("");
            textUser.setText("");
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc="Netbeans code">
        /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        profilesList = new javax.swing.JList<>();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        buttonEdit = new javax.swing.JButton();
        buttonRemove = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        textUser = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        textUrl = new javax.swing.JTextField();
        buttonOpenUrl = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        textAlias = new javax.swing.JTextField();
        buttonReveal = new javax.swing.JButton();
        textPassword = new javax.swing.JPasswordField();
        jPanel7 = new javax.swing.JPanel();
        buttonAdd = new javax.swing.JButton();
        buttonSettings = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(630, 570));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jSplitPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder(11, 11, 11, 11));
        jSplitPane1.setDividerLocation(200);

        profilesList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                profilesListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(profilesList);

        jSplitPane1.setLeftComponent(jScrollPane1);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel4.setBackground(new java.awt.Color(247, 247, 247));
        jPanel4.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 0, 5), javax.swing.BorderFactory.createTitledBorder("OPÇÕES")));

        buttonEdit.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        buttonEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/myid/icons/36322 - pencil.png"))); // NOI18N
        buttonEdit.setText("EDITAR");
        buttonEdit.setIconTextGap(10);
        buttonEdit.setPreferredSize(new java.awt.Dimension(130, 23));
        buttonEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonEditMouseClicked(evt);
            }
        });

        buttonRemove.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        buttonRemove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/myid/icons/35931 - cross.png"))); // NOI18N
        buttonRemove.setText("REMOVER");
        buttonRemove.setIconTextGap(10);
        buttonRemove.setPreferredSize(new java.awt.Dimension(130, 23));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(buttonRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(buttonEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel4, java.awt.BorderLayout.PAGE_END);

        jPanel1.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 5, 5), javax.swing.BorderFactory.createTitledBorder("PERFIL")));
        jPanel1.setLayout(null);
        jPanel1.add(textUser);
        textUser.setBounds(160, 80, 110, 20);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel1.setText("Usuário:");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(60, 80, 90, 20);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel2.setText("Senha:");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(60, 120, 90, 20);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel3.setText("URL:");
        jLabel3.setToolTipText("");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(60, 190, 90, 20);
        jPanel1.add(textUrl);
        textUrl.setBounds(160, 190, 110, 20);

        buttonOpenUrl.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        buttonOpenUrl.setText("ACESSAR");
        buttonOpenUrl.setIconTextGap(10);
        buttonOpenUrl.setPreferredSize(new java.awt.Dimension(130, 23));
        jPanel1.add(buttonOpenUrl);
        buttonOpenUrl.setBounds(160, 220, 110, 20);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel4.setText("Apelido:");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(60, 40, 90, 20);
        jPanel1.add(textAlias);
        textAlias.setBounds(160, 40, 110, 20);

        buttonReveal.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        buttonReveal.setText("REVELAR");
        buttonReveal.setIconTextGap(10);
        buttonReveal.setPreferredSize(new java.awt.Dimension(130, 23));
        buttonReveal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonRevealMouseClicked(evt);
            }
        });
        jPanel1.add(buttonReveal);
        buttonReveal.setBounds(160, 150, 110, 20);
        jPanel1.add(textPassword);
        textPassword.setBounds(160, 120, 110, 20);

        jPanel2.add(jPanel1, java.awt.BorderLayout.CENTER);

        jSplitPane1.setRightComponent(jPanel2);

        getContentPane().add(jSplitPane1, java.awt.BorderLayout.CENTER);

        jPanel7.setBackground(new java.awt.Color(204, 204, 204));
        jPanel7.setPreferredSize(new java.awt.Dimension(0, 90));
        jPanel7.setRequestFocusEnabled(false);

        buttonAdd.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        buttonAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/myid/icons/93209 - netvibes.png"))); // NOI18N
        buttonAdd.setText("ADICIONAR");
        buttonAdd.setIconTextGap(10);
        buttonAdd.setPreferredSize(new java.awt.Dimension(130, 23));
        buttonAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonAddMouseClicked(evt);
            }
        });

        buttonSettings.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        buttonSettings.setIcon(new javax.swing.ImageIcon(getClass().getResource("/myid/icons/64639 - setting tools.png"))); // NOI18N
        buttonSettings.setText("AJUSTES");
        buttonSettings.setIconTextGap(10);
        buttonSettings.setPreferredSize(new java.awt.Dimension(90, 23));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(buttonAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonSettings, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(294, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonAdd, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                    .addComponent(buttonSettings, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        getContentPane().add(jPanel7, java.awt.BorderLayout.NORTH);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void buttonRevealMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonRevealMouseClicked
        textPassword.setEchoChar((char)0);
    }//GEN-LAST:event_buttonRevealMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        onViewLoaded();
    }//GEN-LAST:event_formWindowOpened

    private void buttonAddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonAddMouseClicked
        controller.addNewProfile();
    }//GEN-LAST:event_buttonAddMouseClicked

    private void profilesListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_profilesListValueChanged
        // TODO add your handling code here:
        controller.selectionChanged(profilesList.getSelectedValue());
        updateCurrentProfileView();
    }//GEN-LAST:event_profilesListValueChanged

    private void buttonEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonEditMouseClicked
        // TODO add your handling code here:
        controller.editCurrentProfile();
        updateCurrentProfileView();
    }//GEN-LAST:event_buttonEditMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonAdd;
    private javax.swing.JButton buttonEdit;
    private javax.swing.JButton buttonOpenUrl;
    private javax.swing.JButton buttonRemove;
    private javax.swing.JButton buttonReveal;
    private javax.swing.JButton buttonSettings;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JList<String> profilesList;
    private javax.swing.JTextField textAlias;
    private javax.swing.JPasswordField textPassword;
    private javax.swing.JTextField textUrl;
    private javax.swing.JTextField textUser;
    // End of variables declaration//GEN-END:variables
// </editor-fold> 
    
}
