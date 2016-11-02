package vn.ce.sale;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;

import vn.ce.sale.data.IDataCheck;
import vn.ce.sale.fragment.Fragment_Home_List2;
import vn.ce.sale.fragment.Fragment_Product_List;
import vn.ce.sale.fragment.airtimemix.FragmentVbill;
import vn.ce.sale.fragment.airtimemix.FragmentVtopup;
import vn.ce.sale.fragment.airtimemix.Fragment_Home_Air;
import vn.ce.sale.fragment.airtimemix.Fragment_Report_Vtopup_List;
import vn.ce.sale.fragment.airtimemix.Fragment_VTOPUP_ListTabViewer;
import vn.ce.sale.fragment.bee.BeeFragmentOrderProduct;
import vn.ce.sale.fragment.bee.BeeFragmentReportOrderProduct;
import vn.ce.sale.fragment.bee.Bee_Fragment_Category_Product_List;
import vn.ce.sale.fragment.bee.Bee_Fragment_Home_Vnpost;
import vn.ce.sale.fragment.bee.Bee_Fragment_Product_List;
import vn.ce.sale.fragment.bee.Bee_Fragment_Product_List_Detail;
import vn.ce.sale.fragment.vi21.FragmentChangeOrderProduct;
import vn.ce.sale.fragment.vi21.FragmentOrderCreate;
import vn.ce.sale.fragment.vi21.FragmentOrderCreate_ViewOrConfirm;
import vn.ce.sale.fragment.vi21.FragmentOrderProduct;
import vn.ce.sale.fragment.vi21.Fragment_OrderPurchase_ListStatus1_Online;
import vn.ce.sale.fragment.vi21.Fragment_Order_Create_ListTabViewer;
import vn.ce.sale.fragment.vi21.Fragment_Order_ListTabViewer;
import vn.ce.sale.fragment.vi21.Fragment_Report_Inventory_List;
import vn.ce.sale.fragment.vi21.Fragment_Report_List;
import vn.ce.sale.fragment.vi21.Fragment_Report_Sale_List;
import vn.ce.sale.model.MyWebService;
import vn.ce.sale.ui.BundleData;
import vn.ce.sale.ui.CIDManager;
import vn.ce.sale.ui.ICallBackActivityToFragment;
import vn.ce.sale.ui.ICallBackFragmentToActivity;
import vn.ce.sale.ui.ZopostActivity;
import vn.ce.sale.ui.ZopostFragment;
import vn.ce.sale.util.ShareMemManager;
import vn.ce.sale.util.Util;
import vn.ce.sale.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity1 extends ZopostActivity implements SearchView.OnQueryTextListener,
		NavigationDrawerFragment.NavigationDrawerCallbacks, ICallBackFragmentToActivity {
	public static Date dtLast = null;
	public static Date dtLastBefore = null;
	public static String screenIdLast = null;
	public static String screenIdBefore = null;

	public static int i = 0;

	public static boolean isFirstTime = false;
	private NavigationDrawerFragment mNavigationDrawerFragment;

	boolean checkDialog = false;

	// back
	private ZopostFragment fragMain;;

	// auto update
	Long lastUpdateTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		// if(savedInstanceState!=null) return;
		this.setContentView(R.layout.activity_register1);
		init_UI(this);

	}
	// auto update

	private void setupMenuNavigatorDrawer() {

		// TODO Auto-generated method stub
		mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
		if (!getParams().getString("screen").equalsIgnoreCase(Util.SCREEN_HOME)) {
			getSupportActionBar().show();
		} else {
			getSupportActionBar().hide();
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction().hide(mNavigationDrawerFragment);
		}
	}

	private void init_UI(Activity activity) {

		Util.saveActionUser(getApplicationContext(), "HOME-PAGE", (new Date()).getTime());
		// startRegisterCID();
		ColorDrawable colorDrawable = new ColorDrawable();
		int color = getResources().getColor(R.color.d_color_blue3);
		colorDrawable.setColor(color);
		getSupportActionBar().setBackgroundDrawable(colorDrawable);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setIcon(R.drawable.ic_launcher);
		getSupportActionBar().setCustomView(R.layout.custom_actionbar);
		setupMenuNavigatorDrawer();

		showNavigate(getParams());

		/**/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		return true;

	}

	@Override
	public void onParamsFromFragment(Bundle data) {
		showNavigate(data);

	}

	@Override
	public void onBackPressed() {
		// Toast.makeText(this, "handle back press",Toast.LENGTH_LONG).show();
		if (fragMain instanceof Bee_Fragment_Category_Product_List) {
			AlertDialog.Builder b = new AlertDialog.Builder(HomeActivity1.this);
			b.setCancelable(false);
			b.setMessage("Bạn có muốn thoát chương trình?");
			b.setPositiveButton("Có", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					finish();
				}
			});
			b.setNegativeButton("Không", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			});
			b.create().show();

		} else {

			if (fragMain instanceof FragmentOrderProduct) {
				if (((IDataCheck) fragMain).hasDataSource()) {
					if (Util.checkOrderOnline == true) {
						fragMain = new Fragment_Order_ListTabViewer();
						replaceFragment(fragMain, null, R.id.container, true);
						Util.checkOrderOnline = false;
					} else if (Util.checkSaveOffline == true) {
						AlertDialog.Builder b = new AlertDialog.Builder(HomeActivity1.this);
						b.setCancelable(false);
						b.setMessage("Bạn có muốn lưu dữ liệu?");
						b.setPositiveButton("Có", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								checkDialog = true;
								((IDataCheck) fragMain).saveDataSource();

							}
						});
						b.setNegativeButton("Không", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								checkDialog = true;
								fragMain = new Fragment_Order_ListTabViewer();
								replaceFragment(fragMain, null, R.id.container, true);
								Util.checkPage = false;
							}
						});
						b.create().show();
						// neu ok thi saver
						Util.checkSaveOffline = false;
					}
				} else {
					checkDataSource();
				}

			} else if (fragMain instanceof Fragment_Report_Inventory_List
					|| fragMain instanceof Fragment_Report_Sale_List) {
				if (Util.checkReport == true) {
					getSupportActionBar().hide();
					fragMain = new Fragment_Report_List();
					replaceFragment(fragMain, null, R.id.container, true);
					Util.checkReport = false;
				} else {
					checkDataSource();
				}
			} else if (fragMain instanceof FragmentOrderCreate) {
				checkDataSourceProductCreate();
			} else if (Util.checkPage2 == true) {
				fragMain = new Fragment_Order_Create_ListTabViewer();
				replaceFragment(fragMain, null, R.id.container, true);
				Util.checkPage2 = false;
			} else if (fragMain instanceof FragmentChangeOrderProduct) {
				AlertDialog.Builder b = new AlertDialog.Builder(HomeActivity1.this);
				b.setCancelable(false);
				b.setMessage("Bạn đang thay đổi đơn đặt hàng!");
				b.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						fragMain = new Fragment_Order_ListTabViewer();
						replaceFragment(fragMain, null, R.id.container, true);
					}
				});
				b.setNegativeButton("Thực hiện tiếp", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
				b.create().show();
			} else if (fragMain instanceof FragmentVtopup) {
				String vtopup = ShareMemManager.getInstance().readFromDB(getApplicationContext(), "vtopup");
				if (vtopup.length() > 0) {
					AlertDialog.Builder b = new AlertDialog.Builder(HomeActivity1.this);
					b.setCancelable(false);
					b.setMessage("Bạn chắc chắn muốn hủy giao dịch này!");
					b.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							fragMain = new Fragment_Home_Air();
							replaceFragment(fragMain, null, R.id.container, true);
							ShareMemManager.getInstance().deleteFromDB(getApplicationContext(), "vtopup");
						}
					});
					b.setNegativeButton("Không", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					});
					b.create().show();
				} else
					showNavigate(BundleData.createNew().putString("screen", Util.SCREEN_HOME_AIR).data());
			} else if (fragMain instanceof Bee_Fragment_Product_List_Detail) {
				showNavigate(BundleData.createNew().putString("screen", Util.SCREEN_PRODUCT_ECOM).data());

			} else if (fragMain instanceof Bee_Fragment_Product_List) {
				showNavigate(BundleData.createNew().putString("screen", Util.SCREEN_ALL_PRODUCT_ECOM).data());
			} else if (fragMain instanceof FragmentVbill) {
				String vbill = ShareMemManager.getInstance().readFromDB(getApplicationContext(), "vbill");
				if (vbill.length() > 0) {
					AlertDialog.Builder b = new AlertDialog.Builder(HomeActivity1.this);
					b.setCancelable(false);
					b.setMessage("Bạn chắc chắn muốn hủy giao dịch này!");
					b.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							fragMain = new Fragment_Home_Air();
							replaceFragment(fragMain, null, R.id.container, true);
							ShareMemManager.getInstance().deleteFromDB(getApplicationContext(), "vbill");
						}
					});
					b.setNegativeButton("Không", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					});
					b.create().show();
				} else
					showNavigate(BundleData.createNew().putString("screen", Util.SCREEN_HOME_AIR).data());
			} else
				showNavigate(BundleData.createNew().putString("screen", Util.SCREEN_HOME).data());

		}
	}

	private void checkDataSource() {
		checkDataSourceProduct(Util.SCREEN_HOME);
	}

	private void checkDataSourceProductCreate() {
		checkDataSourceProductCreate(Util.SCREEN_HOME);
	}

	private void checkData() {
		// TODO Auto-generated method stub
		if (((IDataCheck) fragMain).hasDataSource()) {
			// show dialog
			AlertDialog.Builder b = new AlertDialog.Builder(HomeActivity1.this);
			b.setCancelable(true);
			b.setMessage("Bạn có muốn lưu dữ liệu?");
			b.setPositiveButton("Có", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					checkDialog = true;
					((IDataCheck) fragMain).saveDataSource();

				}
			});
			b.setNegativeButton("Không", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					checkDialog = true;
					thoat();
				}
			});
			b.create().show();
			// neu ok thi saver

		} else
			thoat();
	}

	private void checkChangeOrderProduct(final String screen) {
		// TODO Auto-generated method stub

		AlertDialog.Builder b = new AlertDialog.Builder(HomeActivity1.this);
		b.setCancelable(false);
		b.setMessage("Bạn đang thay đổi đơn đặt hàng!");
		b.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				showNavigate(BundleData.createNew().putString("screen", screen).data());
			}
		});
		b.setNegativeButton("Thực hiện tiếp", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		b.create().show();

	}

	private void checkVtopup(final String screen) {
		// TODO Auto-generated method stub
		String vtopup = ShareMemManager.getInstance().readFromDB(getApplicationContext(), "vtopup");
		if (vtopup.length() > 0) {
			AlertDialog.Builder b = new AlertDialog.Builder(HomeActivity1.this);
			b.setCancelable(false);
			b.setMessage("Bạn chắc chắn muốn hủy giao dịch này!");
			b.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					showNavigate(BundleData.createNew().putString("screen", screen).data());
					ShareMemManager.getInstance().deleteFromDB(getApplicationContext(), "vtopup");
				}
			});
			b.setNegativeButton("Không", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			});
			b.create().show();
		} else {
			showNavigate(BundleData.createNew().putString("screen", screen).data());
		}

	}

	private void checkVbill(final String screen) {
		// TODO Auto-generated method stub
		String vbill = ShareMemManager.getInstance().readFromDB(getApplicationContext(), "vbill");
		if (vbill.length() > 0) {
			AlertDialog.Builder b = new AlertDialog.Builder(HomeActivity1.this);
			b.setCancelable(false);
			b.setMessage("Bạn chắc chắn muốn hủy giao dịch này!");
			b.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					showNavigate(BundleData.createNew().putString("screen", screen).data());
					ShareMemManager.getInstance().deleteFromDB(getApplicationContext(), "vbill");
				}
			});
			b.setNegativeButton("Không", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			});
			b.create().show();
		} else
			showNavigate(BundleData.createNew().putString("screen", screen).data());

	}

	private void checkDataSourceProduct(final String screen) {
		// TODO Auto-generated method stub
		if (((IDataCheck) fragMain).hasDataSource()) {
			// show dialog
			AlertDialog.Builder b = new AlertDialog.Builder(HomeActivity1.this);
			b.setCancelable(false);
			b.setMessage("Bạn có muốn lưu dữ liệu?");
			b.setPositiveButton("Có", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					checkDialog = true;
					((IDataCheck) fragMain).saveDataSource();

				}
			});
			b.setNegativeButton("Không", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					checkDialog = true;
					showNavigate(BundleData.createNew().putString("screen", screen).data());
				}
			});
			b.create().show();
			// neu ok thi saver

		} else
			showNavigate(BundleData.createNew().putString("screen", screen).data());
	}

	private void checkDataSourceProductCreate(final String screen) {
		// TODO Auto-generated method stub
		if (((IDataCheck) fragMain).hasDataSource()) {
			// show dialog
			AlertDialog.Builder b = new AlertDialog.Builder(HomeActivity1.this);
			b.setCancelable(false);
			b.setMessage("Bạn có chắc chắn muốn thoát không?");
			b.setPositiveButton("Có", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// ((IDataCheck) fragMain).saveDataSource();
					showNavigate(BundleData.createNew().putString("screen", screen).data());
				}
			});
			b.setNegativeButton("Không", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {

				}
			});
			b.create().show();
			// neu ok thi saver

		} else
			showNavigate(BundleData.createNew().putString("screen", screen).data());
	}

	public void onNavigationDrawerItemSelected(int position) {
		// TODO Auto-generated method stub
		if (position == 0) {
			if (fragMain instanceof FragmentChangeOrderProduct) {
				checkChangeOrderProduct(Util.SCREEN_HOME);
			} else if (fragMain instanceof FragmentVtopup) {
				checkVtopup(Util.SCREEN_HOME);
			} else if (fragMain instanceof FragmentVbill) {
				checkVbill(Util.SCREEN_HOME);
			} else if (fragMain instanceof FragmentOrderProduct && ((IDataCheck) fragMain).hasDataSource()) {
				if (Util.checkOrderOnline == true) {
					showNavigate(BundleData.createNew().putString("screen", Util.SCREEN_HOME).data());
					Util.checkOrderOnline = false;
				} else if (Util.checkSaveOffline == true) {
					checkDataSourceProduct(Util.SCREEN_HOME);
					Util.checkSaveOffline = false;
				}
			} else if (ShareMemManager.getInstance().readFromDB(getApplicationContext(), "checkDialog")
					.equals("true")) {
				showNavigate(BundleData.createNew().putString("screen", Util.SCREEN_HOME).data());
			} else {
				showNavigate(BundleData.createNew().putString("screen", Util.SCREEN_HOME).data());

			}
		} // me
		// else if (position == 1) {
		// if (fragMain instanceof FragmentChangeOrderProduct) {
		// checkChangeOrderProduct(Util.SCREEN_ORDER);
		// } else if (fragMain instanceof FragmentVtopup) {
		// checkVtopup(Util.SCREEN_ORDER);
		// } else if (fragMain instanceof FragmentVbill) {
		// checkVbill(Util.SCREEN_ORDER);
		// } else if (fragMain instanceof FragmentOrderProduct && ((IDataCheck)
		// fragMain).hasDataSource()) {
		// if (Util.checkOrderOnline == true) {
		// showNavigate(BundleData.createNew().putString("screen",
		// Util.SCREEN_ORDER).data());
		// Util.checkOrderOnline = false;
		// } else if (Util.checkSaveOffline == true) {
		// checkDataSourceProduct(Util.SCREEN_ORDER);
		// Util.checkSaveOffline = false;
		// }
		// }
		// // else if (fragMain instanceof FragmentOrderCreate && ((IDataCheck)
		// // fragMain).hasDataSource())
		// // checkDataSourceProduct(Util.SCREEN_ORDER);
		// else
		// showNavigate(BundleData.createNew().putString("screen",
		// Util.SCREEN_ORDER).data());
		// } else if (position == 2) {
		// if (fragMain instanceof FragmentChangeOrderProduct) {
		// checkChangeOrderProduct(Util.SCREEN_ORDER_CREATE);
		// } else if (fragMain instanceof FragmentVtopup) {
		// checkVtopup(Util.SCREEN_ORDER_CREATE);
		// } else if (fragMain instanceof FragmentVbill) {
		// checkVbill(Util.SCREEN_ORDER_CREATE);
		// } else if (fragMain instanceof FragmentOrderProduct && ((IDataCheck)
		// fragMain).hasDataSource()) {
		// if (Util.checkOrderOnline == true) {
		// showNavigate(BundleData.createNew().putString("screen",
		// Util.SCREEN_ORDER_CREATE).data());
		// Util.checkOrderOnline = false;
		// } else if (Util.checkSaveOffline == true) {
		// checkDataSourceProduct(Util.SCREEN_ORDER_CREATE);
		// Util.checkSaveOffline = false;
		// }
		// }
		//
		// else
		// showNavigate(BundleData.createNew().putString("screen",
		// Util.SCREEN_ORDER_CREATE).data());
		//
		// } else if (position == 3) {
		// if (fragMain instanceof FragmentChangeOrderProduct) {
		// checkChangeOrderProduct(Util.SCREEN_REPORT_LIST);
		// } else if (fragMain instanceof FragmentVtopup) {
		// checkVtopup(Util.SCREEN_REPORT_LIST);
		// } else if (fragMain instanceof FragmentVbill) {
		// checkVbill(Util.SCREEN_REPORT_LIST);
		// } else if (fragMain instanceof FragmentOrderProduct && ((IDataCheck)
		// fragMain).hasDataSource()) {
		// if (Util.checkOrderOnline == true) {
		// showNavigate(BundleData.createNew().putString("screen",
		// Util.SCREEN_REPORT_LIST).data());
		// Util.checkOrderOnline = false;
		// } else if (Util.checkSaveOffline == true) {
		// checkDataSourceProduct(Util.SCREEN_ORDER);
		// Util.checkSaveOffline = false;
		// }
		// } else
		// showNavigate(BundleData.createNew().putString("screen",
		// Util.SCREEN_REPORT_LIST).data());
		// }
		else if (position == 1) {
			if (fragMain instanceof FragmentVbill) {
				checkVbill(Util.SCREEN_VTOPUP);
			} else if (fragMain instanceof FragmentVtopup) {
				checkVbill(Util.SCREEN_VTOPUP);
			} else
				showNavigate(BundleData.createNew().putString("screen", Util.SCREEN_VTOPUP).data());
		} else if (position == 2) {
			if (fragMain instanceof FragmentVtopup) {
				checkVtopup(Util.SCREEN_VBILL);
			} else if (fragMain instanceof FragmentVbill) {
				checkVtopup(Util.SCREEN_VBILL);
			} else
				showNavigate(BundleData.createNew().putString("screen", Util.SCREEN_VBILL).data());
		} else if (position == 3) {
			if (fragMain instanceof FragmentVtopup) {
				checkVtopup(Util.SCREEN_AIRTIMEMIX);
			} else if (fragMain instanceof FragmentVbill) {
				checkVbill(Util.SCREEN_AIRTIMEMIX);
			} else
				showNavigate(BundleData.createNew().putString("screen", Util.SCREEN_AIRTIMEMIX).data());
		}
		// else if (position == 7) {
		// showNavigate(BundleData.createNew().putString("screen",
		// Util.SCREEN_ALL_PRODUCT_ECOM).data());
		// } else if (position == 8) {
		// showNavigate(BundleData.createNew().putString("screen",
		// Util.SCREEN_ORDER_ECOM).data());
		// } else if (position == 9) {
		// showNavigate(BundleData.createNew().putString("screen",
		// Util.SCREEN_REPORT_ECOM).data());
		// }
		else if (position == 4) {
			if (fragMain instanceof FragmentOrderProduct && ((IDataCheck) fragMain).hasDataSource()) {
				if (Util.checkOrderOnline == true) {
					thoat();
				} else if (Util.checkSaveOffline == true)
					checkData();
			} else
				thoat();
		}

	}

	private void thoat() {
		// TODO Auto-generated method stub
		AlertDialog.Builder b = new AlertDialog.Builder(HomeActivity1.this);
		b.setCancelable(false);
		b.setMessage("Bạn có muốn thoát chương trình?");
		b.setPositiveButton("Có", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		});
		b.setNegativeButton("Không", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		b.create().show();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		handleIntent(intent);
	}

	@Override
	protected void onResume() {
		// Toast.makeText(this,"OnReums", Toast.LENGTH_LONG).show();
		super.onResume();
		// showNavigate(getParams());
	}

	@SuppressLint("NewApi")
	private void showNavigate(Bundle bx) {
		i++;
		// getSupportActionBar().setTitle(String.valueOf(i)+" times");
		if (bx == null)
			bx = new Bundle();
		String screenString = bx.getString("screen", "-1");

		if (i % 2 == 0)
			dtLast = new Date();
		if (i % 2 == 1)
			dtLastBefore = new Date();

		if (screenIdLast != null && screenIdBefore != null) {
			screenIdBefore = screenIdLast;
			screenIdLast = screenString;
		}
		if (screenIdLast == null)
			screenIdLast = screenString;
		if (screenIdBefore == null)
			screenIdBefore = screenString;

		// getSupportActionBar().setTitle(getSupportActionBar().getTitle()+":"+screenString);
		if (screenString.equalsIgnoreCase("-1"))
			return;

		if (dtLast != null && dtLastBefore != null) {
			if (Math.abs((dtLast.getTime() - dtLastBefore.getTime())) < 100
					&& (!screenIdBefore.equalsIgnoreCase("-1") && !screenIdBefore.equalsIgnoreCase("home"))) {
				return;
			}
		}
		boolean check = false;
		if (screenString.equalsIgnoreCase(Util.SCREEN_SETTING))// setting report
		{
			Util.saveActionUser(getApplicationContext(), "SETTING", (new Date()).getTime());
			nextToActivity(SettingsActivity.class, null);
			// getSupportActionBar().hide();
			// fragMain = new Fragment_Report_List();
			// replaceFragment(fragMain, bx, R.id.container, true);
		} else if (screenString.equalsIgnoreCase(Util.SCREEN_REPORT_LIST))// setting
		{
			getSupportActionBar().hide();
			fragMain = new Fragment_Report_List();
			replaceFragment(fragMain, bx, R.id.container, true);
		} else if (screenString.equalsIgnoreCase(Util.SCREEN_VTOPUP_TAB))// setting
		{
			getSupportActionBar().show();
			fragMain = new Fragment_VTOPUP_ListTabViewer();
			replaceFragment(fragMain, bx, R.id.container, true);
		} else if (screenString.equalsIgnoreCase(Util.SCREEN_VTOPUP))// setting
		{
			getSupportActionBar().show();
			fragMain = new FragmentVtopup();
			replaceFragment(fragMain, bx, R.id.container, true);
		} else if (screenString.equalsIgnoreCase(Util.SCREEN_VBILL))// setting
		{
			getSupportActionBar().show();
			fragMain = new FragmentVbill();
			replaceFragment(fragMain, bx, R.id.container, true);
		} else if (screenString.equalsIgnoreCase(Util.SCREEN_AIRTIMEMIX))// setting
		{
			getSupportActionBar().show();
			fragMain = new Fragment_Report_Vtopup_List();
			replaceFragment(fragMain, bx, R.id.container, true);
		} else if (screenString.equalsIgnoreCase(Util.SCREEN_ALL_PRODUCT_ECOM))// setting
		{
			getSupportActionBar().show();
			fragMain = new Bee_Fragment_Category_Product_List();
			replaceFragment(fragMain, bx, R.id.container, true);
		} else if (screenString.equalsIgnoreCase(Util.SCREEN_PRODUCT_ECOM))// setting
		{
			getSupportActionBar().show();
			fragMain = new Bee_Fragment_Product_List();
			replaceFragment(fragMain, bx, R.id.container, true);
		} else if (screenString.equalsIgnoreCase(Util.SCREEN_ORDER_ECOM))// setting
		{
			getSupportActionBar().show();
			fragMain = new BeeFragmentOrderProduct();
			replaceFragment(fragMain, bx, R.id.container, true);
		} else if (screenString.equalsIgnoreCase(Util.SCREEN_REPORT_ECOM))// setting
		{
			getSupportActionBar().show();
			fragMain = new BeeFragmentReportOrderProduct();
			replaceFragment(fragMain, bx, R.id.container, true);
		} else if (screenString.equalsIgnoreCase(Util.SCREEN_CHANGE_ORDER_PORDUCT))// setting
		{
			// getSupportActionBar().hide();
			fragMain = new FragmentChangeOrderProduct();
			replaceFragment(fragMain, bx, R.id.container, true);
		} else if (screenString.equalsIgnoreCase(Util.SCREEN_REPORT_INVENTORY_LIST))// setting
		{
			getSupportActionBar().show();
			fragMain = new Fragment_Report_Inventory_List();
			replaceFragment(fragMain, bx, R.id.container, true);
		} else if (screenString.equalsIgnoreCase(Util.SCREEN_REPORT_SALE_LIST))// setting
		{
			getSupportActionBar().show();
			fragMain = new Fragment_Report_Sale_List();
			replaceFragment(fragMain, bx, R.id.container, true);
		} else if (screenString.equalsIgnoreCase(Util.SCREEN_PRODUCT)) {

			getSupportActionBar().show();
			fragMain = new Fragment_Product_List();
			replaceFragment(fragMain, bx, R.id.container, true);
		} else if (screenString.equalsIgnoreCase(Util.SCREEN_HOME)) {// màn hình
			getSupportActionBar().show();
			fragMain = new Bee_Fragment_Category_Product_List();
			replaceFragment(fragMain, bx, R.id.container, true);
		} else if (screenString.equalsIgnoreCase(Util.SCREEN_ORDER)) {// bán

			getSupportActionBar().show();
			fragMain = new FragmentOrderProduct();
			replaceFragment(fragMain, bx, R.id.container, true);
		} else if (screenString.equalsIgnoreCase(Util.SCREEN_ORDER_CREATE)) {// tạo
																				// đơn
			getSupportActionBar().show();
			fragMain = new FragmentOrderCreate();
			replaceFragment(fragMain, bx, R.id.container, true); // hàng

		} else if (screenString.equalsIgnoreCase(Util.SCREEN_ORDER_LIST)) {// ds
																			// đơn
																			// hàng
			getSupportActionBar().show();
			fragMain = new Fragment_Order_ListTabViewer();
			replaceFragment(fragMain, bx, R.id.container, true);
		} else if (screenString.equalsIgnoreCase(Util.SCREEN_HOME_AIR)) {// ds
			// đơn
			// hàng
			getSupportActionBar().show();
			fragMain = new Fragment_Home_Air();
			replaceFragment(fragMain, bx, R.id.container, true);
		} else if (screenString.equalsIgnoreCase(Util.SCREEN_HOME_VNPOST)) {// ds
			// đơn
			// hàng
			getSupportActionBar().show();
			fragMain = new Bee_Fragment_Home_Vnpost();
			replaceFragment(fragMain, bx, R.id.container, true);
		}
		// else {
		// getSupportActionBar().show();
		// fragMain = new FragmentOrderProduct();// tạo bán hàng
		// replaceFragment(fragMain, bx, R.id.container, true);
		//
		// }
		// getSupportActionBar().setTitle(getSupportActionBar().getTitle()+">>"+screenString);
	}

	private void handleIntent(Intent intent) {
		// Toast.makeText(this,"Single top intent", Toast.LENGTH_LONG).show();
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);
			((ICallBackActivityToFragment) fragMain)
					.onParamsFromActivity(BundleData.createNew().putString("object", "" + query).data());
		} else
			showNavigate(getParams());
	}

	@Override
	public boolean onQueryTextChange(String arg0) {

		if (arg0 == null)
			return false;
		if (arg0.length() < 3) {
			((ICallBackActivityToFragment) fragMain)
					.onParamsFromActivity(BundleData.createNew().putString("object", "").data());
		}
		if (arg0.length() >= 3) {
			((ICallBackActivityToFragment) fragMain)
					.onParamsFromActivity(BundleData.createNew().putString("object", "" + arg0).data());
		}
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public static int REQUESTCODE_PICTURE = 1001;
	public static int REQUESTCODE_QRCODE = 1002;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// No call for super(). Bug on API Level > 11.
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Checks the orientation of the screen
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			// setContentView(R.layout.homescreen1);
			// setupButton();
			Toast.makeText(this, "loading in landscape", Toast.LENGTH_SHORT).show();
			// replaceFragment(fragMain, getParams(), R.id.container);
		} else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
			Toast.makeText(this, "loading in portrait", Toast.LENGTH_SHORT).show();
			// setContentView(R.layout.homescreen2);
			// setupButton();
			// replaceFragment(fragMain, getParams(), R.id.container);
		}

	}

	private void makeDirTempInSdCard() {
		String[] str = { "mkdir", Util.resourceDir };
		try {
			Process ps = Runtime.getRuntime().exec(str);
			try {
				ps.waitFor();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void startRegisterCID() {
		Util.activeKey = ShareMemManager.getInstance().readIMEI(getApplicationContext());
		android.telephony.TelephonyManager tg = (android.telephony.TelephonyManager) getSystemService(
				Context.TELEPHONY_SERVICE);
		tg.listen(new CIDManager(tg), CIDManager.EVENTS);
	}

	@Override
	public void setSelectedFragment(ZopostFragment selectedFragment) {
		// TODO Auto-generated method stub
		this.fragMain = selectedFragment;

	}

}
