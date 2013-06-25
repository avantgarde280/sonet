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

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.shafiq.myfeedle.core.Myfeedle.Accounts;
import com.shafiq.myfeedle.core.Myfeedle.Widgets;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.shafiq.myfeedle.core.Myfeedle.ACCOUNTS_QUERY;
import static com.shafiq.myfeedle.core.Myfeedle.CHATTER;
import static com.shafiq.myfeedle.core.Myfeedle.CHATTER_URL_ACCESS;
import static com.shafiq.myfeedle.core.Myfeedle.CHATTER_URL_POST;
import static com.shafiq.myfeedle.core.Myfeedle.FACEBOOK;
import static com.shafiq.myfeedle.core.Myfeedle.FACEBOOK_BASE_URL;
import static com.shafiq.myfeedle.core.Myfeedle.FACEBOOK_POST;
import static com.shafiq.myfeedle.core.Myfeedle.FACEBOOK_SEARCH;
import static com.shafiq.myfeedle.core.Myfeedle.FOURSQUARE;
import static com.shafiq.myfeedle.core.Myfeedle.FOURSQUARE_BASE_URL;
import static com.shafiq.myfeedle.core.Myfeedle.FOURSQUARE_CHECKIN;
import static com.shafiq.myfeedle.core.Myfeedle.FOURSQUARE_CHECKIN_NO_SHOUT;
import static com.shafiq.myfeedle.core.Myfeedle.FOURSQUARE_CHECKIN_NO_VENUE;
import static com.shafiq.myfeedle.core.Myfeedle.FOURSQUARE_SEARCH;
import static com.shafiq.myfeedle.core.Myfeedle.IDENTICA;
import static com.shafiq.myfeedle.core.Myfeedle.IDENTICA_BASE_URL;
import static com.shafiq.myfeedle.core.Myfeedle.IDENTICA_UPDATE;
import static com.shafiq.myfeedle.core.Myfeedle.LINKEDIN;
import static com.shafiq.myfeedle.core.Myfeedle.LINKEDIN_BASE_URL;
import static com.shafiq.myfeedle.core.Myfeedle.LINKEDIN_POST;
import static com.shafiq.myfeedle.core.Myfeedle.LINKEDIN_POST_BODY;
import static com.shafiq.myfeedle.core.Myfeedle.MYSPACE;
import static com.shafiq.myfeedle.core.Myfeedle.MYSPACE_BASE_URL;
import static com.shafiq.myfeedle.core.Myfeedle.MYSPACE_STATUSMOOD_BODY;
import static com.shafiq.myfeedle.core.Myfeedle.MYSPACE_URL_STATUSMOOD;
import static com.shafiq.myfeedle.core.Myfeedle.PRO;
import static com.shafiq.myfeedle.core.Myfeedle.SNearby;
import static com.shafiq.myfeedle.core.Myfeedle.Saccess_token;
import static com.shafiq.myfeedle.core.Myfeedle.Sdata;
import static com.shafiq.myfeedle.core.Myfeedle.Sfull_name;
import static com.shafiq.myfeedle.core.Myfeedle.Sgroups;
import static com.shafiq.myfeedle.core.Myfeedle.Sid;
import static com.shafiq.myfeedle.core.Myfeedle.Sitems;
import static com.shafiq.myfeedle.core.Myfeedle.Smessage;
import static com.shafiq.myfeedle.core.Myfeedle.Sname;
import static com.shafiq.myfeedle.core.Myfeedle.Splace;
import static com.shafiq.myfeedle.core.Myfeedle.Splaces;
import static com.shafiq.myfeedle.core.Myfeedle.Sresponse;
import static com.shafiq.myfeedle.core.Myfeedle.Sresult;
import static com.shafiq.myfeedle.core.Myfeedle.Sstatus;
import static com.shafiq.myfeedle.core.Myfeedle.Stags;
import static com.shafiq.myfeedle.core.Myfeedle.Statuses;
import static com.shafiq.myfeedle.core.Myfeedle.TWITTER;
import static com.shafiq.myfeedle.core.Myfeedle.TWITTER_BASE_URL;
import static com.shafiq.myfeedle.core.Myfeedle.TWITTER_SEARCH;
import static com.shafiq.myfeedle.core.Myfeedle.TWITTER_UPDATE;
import static com.shafiq.myfeedle.core.MyfeedleTokens.CHATTER_KEY;
import static com.shafiq.myfeedle.core.MyfeedleTokens.IDENTICA_KEY;
import static com.shafiq.myfeedle.core.MyfeedleTokens.IDENTICA_SECRET;
import static com.shafiq.myfeedle.core.MyfeedleTokens.LINKEDIN_KEY;
import static com.shafiq.myfeedle.core.MyfeedleTokens.LINKEDIN_SECRET;
import static com.shafiq.myfeedle.core.MyfeedleTokens.MYSPACE_KEY;
import static com.shafiq.myfeedle.core.MyfeedleTokens.MYSPACE_SECRET;
import static com.shafiq.myfeedle.core.MyfeedleTokens.TWITTER_KEY;
import static com.shafiq.myfeedle.core.MyfeedleTokens.TWITTER_SECRET;

public class MyfeedleCreatePost extends Activity implements OnKeyListener, OnClickListener, TextWatcher {
	private static final String TAG = "MyfeedleCreatePost";
	private HashMap<Long, String> mAccountsLocation = new HashMap<Long, String>();
	private HashMap<Long, String[]> mAccountsTags = new HashMap<Long, String[]>();
	private HashMap<Long, Integer> mAccountsService = new HashMap<Long, Integer>();
	private EditText mMessage;
	private ImageButton mSend;
	private TextView mCount;
	private String mLat = null;
	private String mLong = null;
	private MyfeedleCrypto mMyfeedleCrypto;
	private static final int PHOTO = 1;
	private static final int TAGS = 2;
	private String mPhotoPath;
	private HttpClient mHttpClient;
	private AlertDialog mDialog;
	private static final List<Integer> sLocationSupported = new ArrayList<Integer>();
	private static final List<Integer> sPhotoSupported = new ArrayList<Integer>();
	private static final List<Integer> sTaggingSupported = new ArrayList<Integer>();

	static {
		sLocationSupported.add(TWITTER);
		sLocationSupported.add(FACEBOOK);
		sLocationSupported.add(FOURSQUARE);
		sPhotoSupported.add(FACEBOOK);
		sTaggingSupported.add(FACEBOOK);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// allow posting to multiple services if an account is defined
		// allow selecting which accounts to use
		// get existing comments, allow liking|unliking those comments
		setContentView(R.layout.post);
		if (!getPackageName().toLowerCase().contains(PRO)) {
			AdView adView = new AdView(this, AdSize.BANNER, MyfeedleTokens.GOOGLE_AD_ID);
			((LinearLayout) findViewById(R.id.ad)).addView(adView);
			adView.loadAd(new AdRequest());
		}
		mMessage = (EditText) findViewById(R.id.message);
		mSend = (ImageButton) findViewById(R.id.send);
		mCount = (TextView) findViewById(R.id.count);
		// load secretkey
		mMyfeedleCrypto = MyfeedleCrypto.getInstance(getApplicationContext());
		mHttpClient = MyfeedleHttpClient.getThreadSafeClient(getApplicationContext());
		mMessage.addTextChangedListener(this);
		mMessage.setOnKeyListener(this);
		mSend.setOnClickListener(this);
		setResult(RESULT_OK);
	}
	
	@Override
	public void onNewIntent(Intent intent) {
		setIntent(intent);
	}

	@Override
	protected void onResume() {
		super.onResume();
		Intent intent = getIntent();
		if (intent != null) {
			String action = intent.getAction();
			if ((action != null) && action.equals(Intent.ACTION_SEND)) {
				if (intent.hasExtra(Intent.EXTRA_STREAM))
					getPhoto((Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM));
				if (intent.hasExtra(Intent.EXTRA_TEXT)) {
					final String text = intent.getStringExtra(Intent.EXTRA_TEXT);
					mMessage.setText(text);
					mCount.setText(Integer.toString(text.length()));
				}
				chooseAccounts();
			} else {
				Uri data = intent.getData();
				if ((data != null) && data.toString().contains(Accounts.getContentUri(this).toString())) {
					// default to the account passed in, but allow selecting additional accounts
					Cursor account = this.getContentResolver().query(Accounts.getContentUri(this), new String[]{Accounts._ID, Accounts.SERVICE}, Accounts._ID + "=?", new String[]{data.getLastPathSegment()}, null);
					if (account.moveToFirst())
						mAccountsService.put(account.getLong(0), account.getInt(1));
					account.close();
				} else if (intent.hasExtra(Widgets.INSTANT_UPLOAD)) {
					// check if a photo path was passed and prompt user to select the account
					setPhoto(intent.getStringExtra(Widgets.INSTANT_UPLOAD));
					chooseAccounts();
				}
			}
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		if ((mDialog != null) && mDialog.isShowing())
			mDialog.dismiss();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_post, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		if (itemId == R.id.menu_post_accounts)
			chooseAccounts();
		else if (itemId == R.id.menu_post_photo) {
			boolean supported = false;
			Iterator<Integer> services = mAccountsService.values().iterator();
			while (services.hasNext() && ((supported = sPhotoSupported.contains(services.next())) == false));
			if (supported) {
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(Intent.createChooser(intent, "Select Picture"), PHOTO);
			} else
				unsupportedToast(sPhotoSupported);
//		} else if (itemId == R.id.menu_post_tags) {
//			if (mAccountsService.size() == 1) {
//				if (sTaggingSupported.contains(mAccountsService.values().iterator().next()))
//					selectFriends(mAccountsService.keySet().iterator().next());
//				else
//					unsupportedToast(sTaggingSupported);
//			} else {
//				// dialog to select an account
//				Iterator<Long> accountIds = mAccountsService.keySet().iterator();
//				HashMap<Long, String> accountEntries = new HashMap<Long, String>();
//				while (accountIds.hasNext()) {
//					Long accountId = accountIds.next();
//					Cursor account = this.getContentResolver().query(Accounts.getContentUri(this), new String[]{Accounts._ID, ACCOUNTS_QUERY}, Accounts._ID + "=?", new String[]{Long.toString(accountId)}, null);
//					if (account.moveToFirst() && sTaggingSupported.contains(mAccountsService.get(accountId)))
//						accountEntries.put(account.getLong(0), account.getString(1));
//				}
//				int size = accountEntries.size();
//				if (size != 0) {
//					final long[] accountIndexes = new long[size];
//					final String[] accounts = new String[size];
//					int i = 0;
//					Iterator<Map.Entry<Long, String>> entries = accountEntries.entrySet().iterator();
//					while (entries.hasNext()) {
//						Map.Entry<Long, String> entry = entries.next();
//						accountIndexes[i] = entry.getKey();
//						accounts[i++] = entry.getValue();
//					}
//					mDialog = (new AlertDialog.Builder(this))
//							.setTitle(R.string.accounts)
//							.setSingleChoiceItems(accounts, -1, new DialogInterface.OnClickListener() {
//								@Override
//								public void onClick(DialogInterface dialog, int which) {
//									selectFriends(accountIndexes[which]);
//									dialog.dismiss();
//								}
//							})
//							.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
//								@Override
//								public void onClick(DialogInterface dialog, int which) {
//									dialog.dismiss();
//								}
//							})
//							.create();
//					mDialog.show();
//				} else
//					unsupportedToast(sTaggingSupported);
//			}
		} else if (itemId == R.id.menu_post_location) {
			LocationManager locationManager = (LocationManager) MyfeedleCreatePost.this.getSystemService(Context.LOCATION_SERVICE);
			Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			if (location != null) {
				mLat = Double.toString(location.getLatitude());
				mLong = Double.toString(location.getLongitude());
				if (mAccountsService.size() == 1) {
					if (sLocationSupported.contains(mAccountsService.values().iterator().next()))
						setLocation(mAccountsService.keySet().iterator().next());
					else
						unsupportedToast(sLocationSupported);
				} else {
					// dialog to select an account
					Iterator<Long> accountIds = mAccountsService.keySet().iterator();
					HashMap<Long, String> accountEntries = new HashMap<Long, String>();
					while (accountIds.hasNext()) {
						Long accountId = accountIds.next();
						Cursor account = this.getContentResolver().query(Accounts.getContentUri(this), new String[]{Accounts._ID, ACCOUNTS_QUERY}, Accounts._ID + "=?", new String[]{Long.toString(accountId)}, null);
						if (account.moveToFirst() && sLocationSupported.contains(mAccountsService.get(accountId)))
							accountEntries.put(account.getLong(account.getColumnIndex(Accounts._ID)), account.getString(account.getColumnIndex(Accounts.USERNAME)));
					}
					int size = accountEntries.size();
					if (size != 0) {
						final long[] accountIndexes = new long[size];
						final String[] accounts = new String[size];
						int i = 0;
						Iterator<Map.Entry<Long, String>> entries = accountEntries.entrySet().iterator();
						while (entries.hasNext()) {
							Map.Entry<Long, String> entry = entries.next();
							accountIndexes[i] = entry.getKey();
							accounts[i++] = entry.getValue();
						}
						mDialog = (new AlertDialog.Builder(this))
								.setTitle(R.string.accounts)
								.setSingleChoiceItems(accounts, -1, new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
										setLocation(accountIndexes[which]);
										dialog.dismiss();
									}
								})
								.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
										dialog.dismiss();
									}
								})
								.create();
						mDialog.show();
					} else
						unsupportedToast(sLocationSupported);
				}
			} else
				(Toast.makeText(this, getString(R.string.location_unavailable), Toast.LENGTH_LONG)).show();
		}
		return super.onOptionsItemSelected(item);
	}

	private void setLocation(final long accountId) {
		final ProgressDialog loadingDialog = new ProgressDialog(this);
		final AsyncTask<Void, Void, String> asyncTask = new AsyncTask<Void, Void, String>() {

			int serviceId;

			@Override
			protected String doInBackground(Void... none) {
				Cursor account = getContentResolver().query(Accounts.getContentUri(MyfeedleCreatePost.this), new String[]{Accounts._ID, Accounts.TOKEN, Accounts.SERVICE, Accounts.SECRET}, Accounts._ID + "=?", new String[]{Long.toString(accountId)}, null);
				if (account.moveToFirst()) {
					MyfeedleOAuth myfeedleOAuth;
					serviceId = account.getInt(account.getColumnIndex(Accounts.SERVICE));
					switch (serviceId) {
					case TWITTER:
						// anonymous requests are rate limited to 150 per hour
						// authenticated requests are rate limited to 350 per hour, so authenticate this!
						myfeedleOAuth = new MyfeedleOAuth(TWITTER_KEY, TWITTER_SECRET, mMyfeedleCrypto.Decrypt(account.getString(account.getColumnIndex(Accounts.TOKEN))), mMyfeedleCrypto.Decrypt(account.getString(account.getColumnIndex(Accounts.SECRET))));
						return MyfeedleHttpClient.httpResponse(mHttpClient, myfeedleOAuth.getSignedRequest(new HttpGet(String.format(TWITTER_SEARCH, TWITTER_BASE_URL, mLat, mLong))));
					case FACEBOOK:
						return MyfeedleHttpClient.httpResponse(mHttpClient, new HttpGet(String.format(FACEBOOK_SEARCH, FACEBOOK_BASE_URL, mLat, mLong, Saccess_token, mMyfeedleCrypto.Decrypt(account.getString(account.getColumnIndex(Accounts.TOKEN))))));
					case FOURSQUARE:
						return MyfeedleHttpClient.httpResponse(mHttpClient, new HttpGet(String.format(FOURSQUARE_SEARCH, FOURSQUARE_BASE_URL, mLat, mLong, mMyfeedleCrypto.Decrypt(account.getString(account.getColumnIndex(Accounts.TOKEN))))));
					}
				}
				account.close();
				return null;
			}

			@Override
			protected void onPostExecute(String response) {
				if (loadingDialog.isShowing()) loadingDialog.dismiss();
				if (response != null) {
					switch (serviceId) {
					case TWITTER:
						try {
							JSONArray places = new JSONObject(response).getJSONObject(Sresult).getJSONArray(Splaces);
							final String placesNames[] = new String[places.length()];
							final String placesIds[] = new String[places.length()];
							for (int i = 0, i2 = places.length(); i < i2; i++) {
								JSONObject place = places.getJSONObject(i);
								placesNames[i] = place.getString(Sfull_name);
								placesIds[i] = place.getString(Sid);
							}
							mDialog = (new AlertDialog.Builder(MyfeedleCreatePost.this))
									.setSingleChoiceItems(placesNames, -1, new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface dialog, int which) {
											mAccountsLocation.put(accountId, placesIds[which]);
											dialog.dismiss();
										}
									})
									.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface dialog, int which) {
											dialog.cancel();
										}
									})
									.create();
							mDialog.show();
						} catch (JSONException e) {
							Log.e(TAG, e.toString());
						}
						break;
					case FACEBOOK:
						try {
							JSONArray places = new JSONObject(response).getJSONArray(Sdata);
							final String placesNames[] = new String[places.length()];
							final String placesIds[] = new String[places.length()];
							for (int i = 0, i2 = places.length(); i < i2; i++) {
								JSONObject place = places.getJSONObject(i);
								placesNames[i] = place.getString(Sname);
								placesIds[i] = place.getString(Sid);
							}
							mDialog = (new AlertDialog.Builder(MyfeedleCreatePost.this))
									.setSingleChoiceItems(placesNames, -1, new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface dialog, int which) {
											mAccountsLocation.put(accountId, placesIds[which]);
											dialog.dismiss();
										}
									})
									.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface dialog, int which) {
											dialog.cancel();
										}
									})
									.create();
							mDialog.show();
						} catch (JSONException e) {
							Log.e(TAG, e.toString());
						}
						break;
					case FOURSQUARE:
						try {
							JSONArray groups = new JSONObject(response).getJSONObject(Sresponse).getJSONArray(Sgroups);
							for (int g = 0, g2 = groups.length(); g < g2; g++) {
								JSONObject group = groups.getJSONObject(g);
								if (group.getString(Sname).equals(SNearby)) {
									JSONArray places = group.getJSONArray(Sitems);
									final String placesNames[] = new String[places.length()];
									final String placesIds[] = new String[places.length()];
									for (int i = 0, i2 = places.length(); i < i2; i++) {
										JSONObject place = places.getJSONObject(i);
										placesNames[i] = place.getString(Sname);
										placesIds[i] = place.getString(Sid);
									}
									mDialog = (new AlertDialog.Builder(MyfeedleCreatePost.this))
											.setSingleChoiceItems(placesNames, -1, new DialogInterface.OnClickListener() {
												@Override
												public void onClick(DialogInterface dialog, int which) {
													mAccountsLocation.put(accountId, placesIds[which]);
													dialog.dismiss();
												}
											})
											.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
												@Override
												public void onClick(DialogInterface dialog, int which) {
													dialog.cancel();
												}
											})
											.create();
									mDialog.show();
									break;
								}
							}
						} catch (JSONException e) {
							Log.e(TAG, e.toString());
						}
						break;
					}
				} else {
					(Toast.makeText(MyfeedleCreatePost.this, getString(R.string.failure), Toast.LENGTH_LONG)).show();
				}
			}

		};
		loadingDialog.setMessage(getString(R.string.loading));
		loadingDialog.setCancelable(true);
		loadingDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {				
			@Override
			public void onCancel(DialogInterface dialog) {
				if (!asyncTask.isCancelled()) asyncTask.cancel(true);
			}
		});
		loadingDialog.setButton(ProgressDialog.BUTTON_NEGATIVE, getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		loadingDialog.show();
		asyncTask.execute();
	}

	private void unsupportedToast(List<Integer> supportedServices) {
		StringBuilder message = new StringBuilder();
		message.append("This feature is currently supported only for ");
		for (int i = 0, l = supportedServices.size(); i < l; i++) {
			message.append(Myfeedle.getServiceName(getResources(), supportedServices.get(i)));
			if (i == (l - 1))
				message.append(".");
			else if (i == (l - 2))
				message.append(", and ");
			else
				message.append(", ");
		}
		Toast.makeText(getApplicationContext(), message.toString(), Toast.LENGTH_LONG).show();
	}

	@Override
	public void onClick(View v) {
		if (v == mSend) {
			if (!mAccountsService.isEmpty()) {
				final ProgressDialog loadingDialog = new ProgressDialog(this);
				final AsyncTask<Void, String, Void> asyncTask = new AsyncTask<Void, String, Void>() {
					@Override
					protected Void doInBackground(Void... arg0) {
						Iterator<Map.Entry<Long, Integer>> entrySet = mAccountsService.entrySet().iterator();
						while (entrySet.hasNext()) {
							Map.Entry<Long, Integer> entry = entrySet.next();
							final long accountId = entry.getKey();
							final int service = entry.getValue();
							final String placeId = mAccountsLocation.get(accountId);
							// post or comment!
							Cursor account = getContentResolver().query(Accounts.getContentUri(MyfeedleCreatePost.this), new String[]{Accounts._ID, Accounts.TOKEN, Accounts.SECRET}, Accounts._ID + "=?", new String[]{Long.toString(accountId)}, null);
							if (account.moveToFirst()) {
								final String serviceName = Myfeedle.getServiceName(getResources(), service);
								publishProgress(serviceName);
								String message;
								MyfeedleOAuth myfeedleOAuth;
								HttpPost httpPost;
								String response = null;
								switch (service) {
								case TWITTER:
									myfeedleOAuth = new MyfeedleOAuth(TWITTER_KEY, TWITTER_SECRET, mMyfeedleCrypto.Decrypt(account.getString(account.getColumnIndex(Accounts.TOKEN))), mMyfeedleCrypto.Decrypt(account.getString(account.getColumnIndex(Accounts.SECRET))));
									// limit tweets to 140, breaking up the message if necessary
									message = mMessage.getText().toString();
									while (message.length() > 0) {
										final String send;
										if (message.length() > 140) {
											// need to break on a word
											int end = 0;
											int nextSpace = 0;
											for (int i = 0, i2 = message.length(); i < i2; i++) {
												end = nextSpace;
												if (message.substring(i, i + 1).equals(" ")) {
													nextSpace = i;
												}
											}
											// in case there are no spaces, just break on 140
											if (end == 0) {
												end = 140;
											}
											send = message.substring(0, end);
											message = message.substring(end + 1);
										} else {
											send = message;
											message = "";
										}
										httpPost = new HttpPost(String.format(TWITTER_UPDATE, TWITTER_BASE_URL));
										// resolve Error 417 Expectation by Twitter
										httpPost.getParams().setBooleanParameter("http.protocol.expect-continue", false);
										List<NameValuePair> params = new ArrayList<NameValuePair>();
										params.add(new BasicNameValuePair(Sstatus, send));
										if (placeId != null) {
											params.add(new BasicNameValuePair("place_id", placeId));
											params.add(new BasicNameValuePair("lat", mLat));
											params.add(new BasicNameValuePair("long", mLong));
										}
										try {
											httpPost.setEntity(new UrlEncodedFormEntity(params));
											response = MyfeedleHttpClient.httpResponse(mHttpClient, myfeedleOAuth.getSignedRequest(httpPost));
										} catch (UnsupportedEncodingException e) {
											Log.e(TAG, e.toString());
										}
										publishProgress(serviceName, getString(response != null ? R.string.success : R.string.failure));
									}
									break;
								case FACEBOOK:
									// handle tags
									StringBuilder tags = null;
									if (mAccountsTags.containsKey(accountId)) {
										String[] accountTags = mAccountsTags.get(accountId);
										if ((accountTags != null) && (accountTags.length > 0)) {
											tags = new StringBuilder();
											tags.append("[");
											String tag_format;
											if (mPhotoPath != null)
												tag_format = "{\"tag_uid\":\"%s\",\"x\":0,\"y\":0}";
											else
												tag_format = "%s";
											for (int i = 0, l = accountTags.length; i < l; i++) {
												if (i > 0)
													tags.append(",");
												tags.append(String.format(tag_format, accountTags[i]));
											}
											tags.append("]");
										}
									}
									if (mPhotoPath != null) {
										// upload photo
										// uploading a photo takes a long time, have the service handle it
										Intent i = Myfeedle.getPackageIntent(MyfeedleCreatePost.this.getApplicationContext(), PhotoUploadService.class);
										i.setAction(Myfeedle.ACTION_UPLOAD);
										i.putExtra(Accounts.TOKEN, account.getString(account.getColumnIndex(Accounts.TOKEN)));
										i.putExtra(Widgets.INSTANT_UPLOAD, mPhotoPath);
										i.putExtra(Statuses.MESSAGE, mMessage.getText().toString());
										i.putExtra(Splace, placeId);
										if (tags != null)
											i.putExtra(Stags, tags.toString());
										startService(i);
										publishProgress(serviceName + " photo");
									} else {
										// regular post
										httpPost = new HttpPost(String.format(FACEBOOK_POST, FACEBOOK_BASE_URL, Saccess_token, mMyfeedleCrypto.Decrypt(account.getString(account.getColumnIndex(Accounts.TOKEN)))));
										List<NameValuePair> params = new ArrayList<NameValuePair>();
										params.add(new BasicNameValuePair(Smessage, mMessage.getText().toString()));
										if (placeId != null)
											params.add(new BasicNameValuePair(Splace, placeId));
										if (tags != null)
											params.add(new BasicNameValuePair(Stags, tags.toString()));
										try {
											httpPost.setEntity(new UrlEncodedFormEntity(params));
											response = MyfeedleHttpClient.httpResponse(mHttpClient, httpPost);
										} catch (UnsupportedEncodingException e) {
											Log.e(TAG,e.toString());
										}
										publishProgress(serviceName, getString(response != null ? R.string.success : R.string.failure));
									}
									break;
								case MYSPACE:
									myfeedleOAuth = new MyfeedleOAuth(MYSPACE_KEY, MYSPACE_SECRET, mMyfeedleCrypto.Decrypt(account.getString(account.getColumnIndex(Accounts.TOKEN))), mMyfeedleCrypto.Decrypt(account.getString(account.getColumnIndex(Accounts.SECRET))));
									try {
										HttpPut httpPut = new HttpPut(String.format(MYSPACE_URL_STATUSMOOD, MYSPACE_BASE_URL));
										httpPut.setEntity(new StringEntity(String.format(MYSPACE_STATUSMOOD_BODY, mMessage.getText().toString())));
										response = MyfeedleHttpClient.httpResponse(mHttpClient, myfeedleOAuth.getSignedRequest(httpPut));
									} catch (IOException e) {
										Log.e(TAG, e.toString());
									}
									// warn users about myspace permissions
									if (response != null) {
										publishProgress(serviceName, getString(R.string.success));
									} else {
										publishProgress(serviceName, getString(R.string.failure) + " " + getString(R.string.myspace_permissions_message));
									}
									break;
								case FOURSQUARE:
									try {
										message = URLEncoder.encode(mMessage.getText().toString(), "UTF-8");
										if (placeId != null) {
											if (message != null) {
												httpPost = new HttpPost(String.format(FOURSQUARE_CHECKIN, FOURSQUARE_BASE_URL, placeId, message, mLat, mLong, mMyfeedleCrypto.Decrypt(account.getString(account.getColumnIndex(Accounts.TOKEN)))));
											} else {
												httpPost = new HttpPost(String.format(FOURSQUARE_CHECKIN_NO_SHOUT, FOURSQUARE_BASE_URL, placeId, mLat, mLong, mMyfeedleCrypto.Decrypt(account.getString(account.getColumnIndex(Accounts.TOKEN)))));
											}
										} else {
											httpPost = new HttpPost(String.format(FOURSQUARE_CHECKIN_NO_VENUE, FOURSQUARE_BASE_URL, message, mMyfeedleCrypto.Decrypt(account.getString(account.getColumnIndex(Accounts.TOKEN)))));
										}
										response = MyfeedleHttpClient.httpResponse(mHttpClient, httpPost);
									} catch (UnsupportedEncodingException e) {
										Log.e(TAG, e.toString());
									}
									publishProgress(serviceName, getString(response != null ? R.string.success : R.string.failure));
									break;
								case LINKEDIN:
									myfeedleOAuth = new MyfeedleOAuth(LINKEDIN_KEY, LINKEDIN_SECRET, mMyfeedleCrypto.Decrypt(account.getString(account.getColumnIndex(Accounts.TOKEN))), mMyfeedleCrypto.Decrypt(account.getString(account.getColumnIndex(Accounts.SECRET))));
									try {
										httpPost = new HttpPost(String.format(LINKEDIN_POST, LINKEDIN_BASE_URL));
										httpPost.setEntity(new StringEntity(String.format(LINKEDIN_POST_BODY, "", mMessage.getText().toString())));
										httpPost.addHeader(new BasicHeader("Content-Type", "application/xml"));
										response = MyfeedleHttpClient.httpResponse(mHttpClient, myfeedleOAuth.getSignedRequest(httpPost));
									} catch (IOException e) {
										Log.e(TAG, e.toString());
									}
									publishProgress(serviceName, getString(response != null ? R.string.success : R.string.failure));
									break;
								case IDENTICA:
									myfeedleOAuth = new MyfeedleOAuth(IDENTICA_KEY, IDENTICA_SECRET, mMyfeedleCrypto.Decrypt(account.getString(account.getColumnIndex(Accounts.TOKEN))), mMyfeedleCrypto.Decrypt(account.getString(account.getColumnIndex(Accounts.SECRET))));
									// limit tweets to 140, breaking up the message if necessary
									message = mMessage.getText().toString();
									while (message.length() > 0) {
										final String send;
										if (message.length() > 140) {
											// need to break on a word
											int end = 0;
											int nextSpace = 0;
											for (int i = 0, i2 = message.length(); i < i2; i++) {
												end = nextSpace;
												if (message.substring(i, i + 1).equals(" ")) {
													nextSpace = i;
												}
											}
											// in case there are no spaces, just break on 140
											if (end == 0) {
												end = 140;
											}
											send = message.substring(0, end);
											message = message.substring(end + 1);
										} else {
											send = message;
											message = "";
										}
										httpPost = new HttpPost(String.format(IDENTICA_UPDATE, IDENTICA_BASE_URL));
										// resolve Error 417 Expectation by Twitter
										httpPost.getParams().setBooleanParameter("http.protocol.expect-continue", false);
										List<NameValuePair> params = new ArrayList<NameValuePair>();
										params.add(new BasicNameValuePair(Sstatus, send));
										if (placeId != null) {
											params.add(new BasicNameValuePair("place_id", placeId));
											params.add(new BasicNameValuePair("lat", mLat));
											params.add(new BasicNameValuePair("long", mLong));
										}
										try {
											httpPost.setEntity(new UrlEncodedFormEntity(params));
											response = MyfeedleHttpClient.httpResponse(mHttpClient, myfeedleOAuth.getSignedRequest(httpPost));
										} catch (UnsupportedEncodingException e) {
											Log.e(TAG, e.toString());
										}
										publishProgress(serviceName, getString(response != null ? R.string.success : R.string.failure));
									}
									break;
								case CHATTER:
									// need to get an updated access_token
									response = MyfeedleHttpClient.httpResponse(mHttpClient, new HttpPost(String.format(CHATTER_URL_ACCESS, CHATTER_KEY, mMyfeedleCrypto.Decrypt(account.getString(account.getColumnIndex(Accounts.TOKEN))))));
									if (response != null) {
										try {
											JSONObject jobj = new JSONObject(response);
											if (jobj.has("instance_url") && jobj.has(Saccess_token)) {
												httpPost = new HttpPost(String.format(CHATTER_URL_POST, jobj.getString("instance_url"), Uri.encode(mMessage.getText().toString())));
												httpPost.setHeader("Authorization", "OAuth " + jobj.getString(Saccess_token));
												response = MyfeedleHttpClient.httpResponse(mHttpClient, httpPost);
											}
										} catch (JSONException e) {
											Log.e(TAG, serviceName + ":" + e.toString());
											Log.e(TAG, response);
										}
									}
									publishProgress(serviceName, getString(response != null ? R.string.success : R.string.failure));
									break;
								}
							}
							account.close();
						}
						return null;
					}

					@Override
					protected void onProgressUpdate(String... params) {
						if (params.length == 1) {
							loadingDialog.setMessage(String.format(getString(R.string.sending), params[0]));
						} else {
							(Toast.makeText(MyfeedleCreatePost.this, params[0] + " " + params[1], Toast.LENGTH_LONG)).show();
						}
					}

					@Override
					protected void onPostExecute(Void result) {
						if (loadingDialog.isShowing()) loadingDialog.dismiss();
						finish();
					}

				};
				loadingDialog.setMessage(getString(R.string.loading));
				loadingDialog.setCancelable(true);
				loadingDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {				
					@Override
					public void onCancel(DialogInterface dialog) {
						if (!asyncTask.isCancelled()) asyncTask.cancel(true);
					}
				});
				loadingDialog.setButton(ProgressDialog.BUTTON_NEGATIVE, getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
				loadingDialog.show();
				asyncTask.execute();
			} else
				(Toast.makeText(MyfeedleCreatePost.this, "no accounts selected", Toast.LENGTH_LONG)).show();
		}
	}

	protected void getPhoto(Uri uri) {
		final ProgressDialog loadingDialog = new ProgressDialog(this);
		final AsyncTask<Uri, Void, String> asyncTask = new AsyncTask<Uri, Void, String>() {
			@Override
			protected String doInBackground(Uri... imgUri) {
				String[] projection = new String[]{MediaStore.Images.Media.DATA};
				String path = null;
				Cursor c = getContentResolver().query(imgUri[0], projection, null, null, null);
				if ((c != null) && c.moveToFirst()) {
					path = c.getString(c.getColumnIndex(projection[0]));
				} else {
					// some file manages send the path through the uri
					path = imgUri[0].getPath();
				}
				c.close();
				return path;
			}

			@Override
			protected void onPostExecute(String path) {
				if (loadingDialog.isShowing()) loadingDialog.dismiss();
				if (path != null)
					setPhoto(path);
				else
					(Toast.makeText(MyfeedleCreatePost.this, "error retrieving the photo path", Toast.LENGTH_LONG)).show();
			}
		};
		loadingDialog.setMessage(getString(R.string.loading));
		loadingDialog.setCancelable(true);
		loadingDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {				
			@Override
			public void onCancel(DialogInterface dialog) {
				if (!asyncTask.isCancelled()) asyncTask.cancel(true);
			}
		});
		loadingDialog.setButton(ProgressDialog.BUTTON_NEGATIVE, getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		loadingDialog.show();
		asyncTask.execute(uri);
	}

	protected void setPhoto(String path) {
		mPhotoPath = path;
		(Toast.makeText(MyfeedleCreatePost.this, "Currently, the photo will only be uploaded Facebook accounts.", Toast.LENGTH_LONG)).show();
	}

	protected void selectFriends(long accountId) {
		if ((mAccountsService.get(accountId) == FACEBOOK) && (!mAccountsLocation.containsKey(accountId) || (mAccountsLocation.get(accountId) == null)))
			(Toast.makeText(MyfeedleCreatePost.this, "To tag friends, Facebook requires a location to be included.", Toast.LENGTH_LONG)).show();
		else
			startActivityForResult(Myfeedle.getPackageIntent(this, SelectFriends.class).putExtra(Accounts.SID, accountId).putExtra(Stags, mAccountsTags.get(accountId)), TAGS);
	}

	protected void chooseAccounts() {
		// don't limit accounts to the widget...
		Cursor c = this.getContentResolver().query(Accounts.getContentUri(this), new String[]{Accounts._ID, ACCOUNTS_QUERY, Accounts.SERVICE}, null, null, null);
		if (c.moveToFirst()) {
			int i = 0;;
			int count = c.getCount();
			final long[] accountIndexes = new long[count];
			final String[] accounts = new String[count];
			final boolean[] defaults = new boolean[count];
			final int[] accountServices = new int[count];
			while (!c.isAfterLast()) {
				long id = c.getLong(0);
				accountIndexes[i] = id;
				accounts[i] = c.getString(1);
				accountServices[i] = c.getInt(2);
				defaults[i++] = mAccountsService.containsKey(id);
				c.moveToNext();
			}
			mDialog = (new AlertDialog.Builder(this))
					.setTitle(R.string.accounts)
					.setMultiChoiceItems(accounts, defaults, new DialogInterface.OnMultiChoiceClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which, boolean isChecked) {
							if (isChecked) {
								final long accountId = accountIndexes[which];
								mAccountsService.put(accountId, accountServices[which]);
								if (sLocationSupported.contains(accountServices[which])) {
									if (mLat == null) {
										LocationManager locationManager = (LocationManager) MyfeedleCreatePost.this.getSystemService(Context.LOCATION_SERVICE);
										Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
										if (location != null) {
											mLat = Double.toString(location.getLatitude());
											mLong = Double.toString(location.getLongitude());
										}										
									}
									if ((mLat != null) && (mLong != null)) {
										dialog.cancel();
										mDialog = (new AlertDialog.Builder(MyfeedleCreatePost.this))
												.setTitle(R.string.set_location)
												.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
													@Override
													public void onClick(DialogInterface dialog, int which) {
														setLocation(accountId);
														dialog.dismiss();
													}
												})
												.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
													@Override
													public void onClick(DialogInterface dialog, int which) {
														dialog.dismiss();
													}
												})
												.create();
										mDialog.show();
									}
								}
							} else {
								mAccountsService.remove(accountIndexes[which]);
								mAccountsLocation.remove(accountIndexes[which]);
								mAccountsTags.remove(accountIndexes[which]);
							}
						}
					})
					.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					})
					.create();
			mDialog.show();
		}
		c.close();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case PHOTO:
			if (resultCode == RESULT_OK) {
				getPhoto(data.getData());
			}
			break;
		case TAGS:
			if ((resultCode == RESULT_OK) && data.hasExtra(Stags) && data.hasExtra(Accounts.SID))
				mAccountsTags.put(data.getLongExtra(Accounts.SID, Myfeedle.INVALID_ACCOUNT_ID), data.getStringArrayExtra(Stags));
			break;
		}
	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		mCount.setText(Integer.toString(mMessage.getText().toString().length()));
		return false;
	}

	@Override
	public void afterTextChanged(Editable arg0) {
		mCount.setText(Integer.toString(arg0.toString().length()));
	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
	}

}