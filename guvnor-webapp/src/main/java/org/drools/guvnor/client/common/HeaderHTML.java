/*
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
package org.drools.guvnor.client.common;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class HeaderHTML extends Composite {

    interface HeaderHTMLBinder
        extends
        UiBinder<Widget, HeaderHTML> {
    }

    private static HeaderHTMLBinder uiBinder = GWT.create( HeaderHTMLBinder.class );

    @UiField
    Label                           textLabel;

    @UiField
    Image                           image;

    public HeaderHTML() {
        initWidget( uiBinder.createAndBindUi( this ) );
    }

    public void setText(String text) {
        textLabel.setText( text );
    }

    public void setImageResource(ImageResource imageResource) {
        image.setResource( imageResource );
    }
}
