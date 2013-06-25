/*
 * Myfeedle - Android Social Networking Widget
 * Copyright (C) 2013 Mohd Shafiq Mat Daud
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Mohd Shafiq Mat Daud avantgarde280@gmail.com
 */

package com.shafiq.myfeedle.core;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import java.lang.ref.WeakReference;

import static com.shafiq.myfeedle.core.Myfeedle.sBFOptions;

public class BitmapDownloadTask extends AsyncTask<String, Void, Bitmap> {
	
	private final WeakReference<ImageView> mImageViewReference;
	private final HttpClient mHttpClient;
	
	public BitmapDownloadTask(ImageView imageView, HttpClient httpClient) {
		mImageViewReference = new WeakReference<ImageView>(imageView);
		mHttpClient = httpClient;
	}

	@Override
	protected Bitmap doInBackground(String... params) {
		byte[] blob = MyfeedleHttpClient.httpBlobResponse(mHttpClient, new HttpGet(params[0]));
		return BitmapFactory.decodeByteArray(blob, 0, blob.length, sBFOptions);
	}

	@Override
	protected void onPostExecute(Bitmap bitmap) {
        if (mImageViewReference != null) {
            ImageView imageView = mImageViewReference.get();
            BitmapDownloadTask bitmapDownloadTask = getBitmapDownloadTask(imageView);
            if (this == bitmapDownloadTask)
                imageView.setImageBitmap(bitmap);
        }
	}
	
    private static BitmapDownloadTask getBitmapDownloadTask(ImageView imageView) {
        if (imageView != null) {
            Drawable drawable = imageView.getDrawable();
            if (drawable instanceof DownloadedDrawable) {
                DownloadedDrawable downloadedDrawable = (DownloadedDrawable)drawable;
                return downloadedDrawable.getBitmapDownloadTask();
            }
        }
        return null;
    }
	
	static class DownloadedDrawable extends ColorDrawable {
        private final WeakReference<BitmapDownloadTask> bitmapDownloadTaskReference;

        public DownloadedDrawable(BitmapDownloadTask bitmapDownloadTask) {
            super(Color.BLACK);
            bitmapDownloadTaskReference = new WeakReference<BitmapDownloadTask>(bitmapDownloadTask);
        }

        public BitmapDownloadTask getBitmapDownloadTask() {
            return bitmapDownloadTaskReference.get();
        }
    }

}
