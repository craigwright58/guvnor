/**
 * Copyright 2010 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.drools.guvnor.client.explorer;

import java.util.HashMap;
import java.util.Map;

import org.drools.guvnor.client.common.GenericCallback;
import org.drools.guvnor.client.images.Images;
import org.drools.guvnor.client.messages.Constants;
import org.drools.guvnor.client.rpc.PackageConfigData;
import org.drools.guvnor.client.rpc.RepositoryServiceFactory;
import org.drools.guvnor.client.rpc.SnapshotInfo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;


public class DeploymentTree extends AbstractTree {
    private static Constants constants = GWT.create(Constants.class);
    private static Images images = (Images) GWT.create(Images.class);       

    private Map<TreeItem, String> itemWidgets = new HashMap<TreeItem, String>();

    private boolean deploymentPackagesLoaded = false;
    
    public DeploymentTree(ExplorerViewCenterPanel tabbedPanel) {
        super(tabbedPanel);
        this.name = constants.PackageSnapshots();
        this.image = images.deploy();
        
        mainTree = ExplorerNodeConfig.getDeploymentTree(itemWidgets);

        //Add Selection listener
        mainTree.addSelectionHandler(this);
        mainTree.addOpenHandler(
				new OpenHandler<TreeItem>() {
					public void onOpen(OpenEvent<TreeItem> event) {
						final TreeItem node = event.getTarget();
						if (ExplorerNodeConfig.PACKAGE_SNAPSHOTS.equals(itemWidgets.get(node))) { 
							return;
						}
						final PackageConfigData conf = (PackageConfigData) node.getUserObject();
						if (conf != null) {
							RepositoryServiceFactory.getService().listSnapshots(conf.name,
									new GenericCallback<SnapshotInfo[]>() {
										public void onSuccess(SnapshotInfo[] snaps) {
											for (final SnapshotInfo snapInfo : snaps) {
												TreeItem snap = new TreeItem(snapInfo.name);
												//snap.setTooltip(snapInfo.comment);
												//snap.setText(snapInfo.name);
												snap.setUserObject(new Object[] { snapInfo, conf });
												node.addItem(snap);
											}
											node.removeItem(node.getChild(0));
										}
									});
						}
					}
				});
    }
    
    public void onSelection(SelectionEvent<TreeItem> event) {
        TreeItem item = event.getSelectedItem();
        
        if (item.getUserObject() instanceof Object[]) {
            Object[] o = (Object[]) item.getUserObject();
            final String snapName = ((SnapshotInfo)o[0]).name;
            PackageConfigData conf = (PackageConfigData)o[1];
            RepositoryServiceFactory.getService().listSnapshots(conf.name, new GenericCallback<SnapshotInfo[]>() {
                public void onSuccess(SnapshotInfo[] a) {
                    for(SnapshotInfo snap : a) {
                    	if (snap.name.equals(snapName)) {
                    		centertabbedPanel.openSnapshot(snap);
                    		return;
                    	}
                    }
                }
            });
        }
    }

}