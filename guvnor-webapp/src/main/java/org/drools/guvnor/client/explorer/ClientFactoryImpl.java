/*
 * Copyright 2011 JBoss Inc
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package org.drools.guvnor.client.explorer;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.PlaceController;

public class ClientFactoryImpl implements ClientFactory {

    private final EventBus eventBus = new SimpleEventBus();
    private final PlaceController placeController = new PlaceController(eventBus);
    private final AuthorPerspectiveView authorPerspectiveView = new AuthorPerspectiveViewImpl();
    private PerspectivesPanelView perspectivesPanelView;

    public PlaceController getPlaceController() {
        return placeController;
    }

    public AuthorPerspectiveView getAuthorPerspectiveView() {
        return authorPerspectiveView;
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    public PerspectivesPanelView getPerspectivesPanelView(boolean showTitle) {
        if (perspectivesPanelView == null) {
            perspectivesPanelView = new PerspectivesPanelViewImpl(showTitle);
        }
        return perspectivesPanelView;
    }

    public IFramePerspectiveView getIFramePerspectiveView() {
        return new IFramePerspectiveViewImpl();
    }
}
