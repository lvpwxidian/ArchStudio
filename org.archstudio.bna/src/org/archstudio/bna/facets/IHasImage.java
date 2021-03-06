package org.archstudio.bna.facets;

import org.archstudio.bna.IThing.IThingKey;
import org.archstudio.bna.keys.ThingKey;
import org.eclipse.swt.graphics.ImageData;

public interface IHasImage {

	public static final IThingKey<ImageData> IMAGE_DATA_KEY = ThingKey.create("imageData");

	public ImageData getImageData();
}
