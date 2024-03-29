package com.peakmain.sdk;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.ToggleButton;

import androidx.annotation.Keep;
import androidx.appcompat.widget.SwitchCompat;

import com.peakmain.sdk.constants.SensorsDataConstants;
import com.peakmain.sdk.manager.SensorsDataManager;
import com.peakmain.sdk.utils.AopUtils;
import com.peakmain.sdk.utils.LogManager;
import com.peakmain.sdk.utils.SensorsDataUtils;

import org.json.JSONObject;

import java.util.Locale;

/**
 * author:Peakmain
 * createTime:2021/6/15
 * mail:2726449200@qq.com
 * describe：
 */
public class SensorsDataAutoTrackHelper {
    @Keep
    public static void trackViewOnClick(DialogInterface dialogInterface, int whichButton) {
        try {
            Dialog dialog = null;
            if (dialogInterface instanceof Dialog) {
                dialog = (Dialog) dialogInterface;
            }

            if (dialog == null) {
                return;
            }

            Context context = dialog.getContext();
            //将Context转成Activity
            Activity activity = SensorsDataUtils.getActivityFromContext(context);

            if (activity == null) {
                activity = dialog.getOwnerActivity();
            }

            JSONObject properties = SensorsDataManager.buildPageInfo(activity);

            Button button = null;
            if (dialog instanceof android.app.AlertDialog) {
                button = ((android.app.AlertDialog) dialog).getButton(whichButton);
            } else if (dialog instanceof AlertDialog) {
                button = ((AlertDialog) dialog).getButton(whichButton);
            } else if (dialog instanceof androidx.appcompat.app.AlertDialog) {
                button = ((androidx.appcompat.app.AlertDialog) dialog).getButton(whichButton);
            }

            if (button != null) {
                properties.put(SensorsDataConstants.ELEMENT_CONTENT, button.getText());
            }

            properties.put(SensorsDataConstants.ELEMENT_TYPE, "Dialog");

            SensorsDataAPI.getInstance().track(SensorsDataConstants.APP_VIEW_CLICK__EVENT_NAME, properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Keep
    public static void trackViewOnClick(CompoundButton view, boolean isChecked) {
        try {
            if (SensorsDataUtils.isDoubleClick(view)) {
                return;
            }
            Context context = view.getContext();
            if (context == null) {
                return;
            }


            Activity activity = SensorsDataUtils.getActivityFromContext(context);
            JSONObject properties = SensorsDataManager.buildPageInfo(activity);

            try {
                String idString = context.getResources().getResourceEntryName(view.getId());
                if (!TextUtils.isEmpty(idString)) {
                    properties.put(SensorsDataConstants.ELEMENT_ID, idString);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            String viewText = null;
            String viewType;
            if (view instanceof CheckBox) {
                viewType = "CheckBox";
                CheckBox checkBox = (CheckBox) view;
                if (!TextUtils.isEmpty(checkBox.getText())) {
                    viewText = checkBox.getText().toString();
                }
            } else if (view instanceof SwitchCompat) {
                viewType = "SwitchCompat";
                SwitchCompat switchCompat = (SwitchCompat) view;
                if (isChecked) {
                    if (!TextUtils.isEmpty(switchCompat.getTextOn())) {
                        viewText = switchCompat.getTextOn().toString();
                    }
                } else {
                    if (!TextUtils.isEmpty(switchCompat.getTextOff())) {
                        viewText = switchCompat.getTextOff().toString();
                    }
                }
            } else if (view instanceof ToggleButton) {
                viewType = "ToggleButton";
                ToggleButton toggleButton = (ToggleButton) view;
                if (isChecked) {
                    if (!TextUtils.isEmpty(toggleButton.getTextOn())) {
                        viewText = toggleButton.getTextOn().toString();
                    }
                } else {
                    if (!TextUtils.isEmpty(toggleButton.getTextOff())) {
                        viewText = toggleButton.getTextOff().toString();
                    }
                }
            } else if (view instanceof RadioButton) {
                viewType = "RadioButton";
                RadioButton radioButton = (RadioButton) view;
                if (!TextUtils.isEmpty(radioButton.getText())) {
                    viewText = radioButton.getText().toString();
                }
            } else {
                viewType = view.getClass().getCanonicalName();
            }

            //Content
            if (!TextUtils.isEmpty(viewText)) {
                properties.put(SensorsDataConstants.ELEMENT_CONTENT, viewText);
            }

            if (!TextUtils.isEmpty(viewType)) {
                properties.put(SensorsDataConstants.ELEMENT_TYPE, viewType);
            }

            properties.put("isChecked", isChecked);

            SensorsDataAPI.getInstance().track(SensorsDataConstants.APP_VIEW_CLICK__EVENT_NAME, properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Keep
    public static void trackViewOnClick(DialogInterface dialogInterface, int whichButton, boolean isChecked) {
        try {
            Dialog dialog = null;
            if (dialogInterface instanceof Dialog) {
                dialog = (Dialog) dialogInterface;
            }

            if (dialog == null) {
                return;
            }

            Context context = dialog.getContext();
            //将Context转成Activity
            Activity activity = SensorsDataUtils.getActivityFromContext(context);

            if (activity == null) {
                activity = dialog.getOwnerActivity();
            }

            JSONObject properties = SensorsDataManager.buildPageInfo(activity);

            ListView listView = null;
            if (dialog instanceof android.app.AlertDialog) {
                listView = ((android.app.AlertDialog) dialog).getListView();
            } else if (dialog instanceof AlertDialog) {
                listView = ((AlertDialog) dialog).getListView();
            }

            if (listView != null) {
                ListAdapter listAdapter = listView.getAdapter();
                Object object = listAdapter.getItem(whichButton);
                if (object != null) {
                    if (object instanceof String) {
                        properties.put(SensorsDataConstants.ELEMENT_CONTENT, object);
                    }
                }
            }

            properties.put("isChecked", isChecked);
            properties.put(SensorsDataConstants.ELEMENT_TYPE, "Dialog");

            SensorsDataAPI.getInstance().track(SensorsDataConstants.APP_VIEW_CLICK__EVENT_NAME, properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * MenuItem 被点击，自动埋点
     *
     * @param object   Object
     * @param menuItem MenuItem
     */
    @Keep
    public static void trackViewOnClick(Object object, MenuItem menuItem) {
        try {
            Context context = null;
            if (object instanceof Context) {
                context = (Context) object;
            }
            Activity activity = SensorsDataUtils.getActivityFromContext(context);
            JSONObject jsonObject = SensorsDataManager.buildPageInfo(activity);
            jsonObject.put(SensorsDataConstants.ELEMENT_TYPE, "menuItem");

            jsonObject.put(SensorsDataConstants.ELEMENT_CONTENT, menuItem.getTitle());

            if (context != null) {
                String idString = null;
                try {
                    idString = context.getResources().getResourceEntryName(menuItem.getItemId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (!TextUtils.isEmpty(idString)) {
                    jsonObject.put(SensorsDataConstants.ELEMENT_ID, idString);
                }
            }

            SensorsDataAPI.getInstance().track(SensorsDataConstants.APP_VIEW_CLICK__EVENT_NAME, jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Keep
    public static void trackTabHost(String tabName) {
        try {
            JSONObject properties = new JSONObject();

            properties.put(SensorsDataConstants.ELEMENT_TYPE, "TabHost");
            properties.put(SensorsDataConstants.ELEMENT_CONTENT, tabName);
            SensorsDataAPI.getInstance().track(SensorsDataConstants.APP_VIEW_CLICK__EVENT_NAME, properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Keep
    public static void trackExpandableListViewGroupOnClick(ExpandableListView expandableListView, View view,
                                                           int groupPosition) {
        trackExpandableListViewChildOnClick(expandableListView, view, groupPosition, -1);
    }

    @Keep
    public static void trackExpandableListViewChildOnClick(ExpandableListView expandableListView, View view,
                                                           int groupPosition, int childPosition) {
        try {
            if (SensorsDataUtils.isDoubleClick(view)) {
                return;
            }
            Context context = expandableListView.getContext();
            if (context == null) {
                return;
            }

            Activity activity = SensorsDataUtils.getActivityFromContext(context);
            JSONObject properties = SensorsDataManager.buildPageInfo(activity);
            // 获取 view 所在的 fragment
            Object fragment = AopUtils.getFragmentFromView(expandableListView,activity);
            if (childPosition != -1) {
                properties.put(SensorsDataConstants.ELEMENT_POSITION, String.format(Locale.CHINA, "%d:%d", groupPosition, childPosition));
            } else {
                properties.put(SensorsDataConstants.ELEMENT_POSITION, String.format(Locale.CHINA, "%d", groupPosition));
            }

            String idString = SensorsDataUtils.getViewId(expandableListView);
            if (!TextUtils.isEmpty(idString)) {
                properties.put(SensorsDataConstants.ELEMENT_ID, idString);
            }

            properties.put(SensorsDataConstants.ELEMENT_TYPE, "ExpandableListView");

            String viewText = null;
            if (view instanceof ViewGroup) {
                try {
                    StringBuilder stringBuilder = new StringBuilder();
                    viewText = SensorsDataUtils.traverseViewContent(stringBuilder, (ViewGroup) view);
                    if (!TextUtils.isEmpty(viewText)) {
                        viewText = viewText.substring(0, viewText.length() - 1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (!TextUtils.isEmpty(viewText)) {
                properties.put(SensorsDataConstants.ELEMENT_CONTENT, viewText);
            }
            if (fragment != null) {
                SensorsDataManager.getScreenNameAndTitleFromFragment(properties, fragment, activity);
            }
            SensorsDataAPI.getInstance().track(SensorsDataConstants.APP_VIEW_CLICK__EVENT_NAME, properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Keep
    public static void trackViewOnClick(android.widget.AdapterView adapterView, View view, int position) {
        if (SensorsDataUtils.isDoubleClick(view)) {
            return;
        }
        try {
            Context context = adapterView.getContext();
            if (context == null) {
                return;
            }

            Activity activity = SensorsDataUtils.getActivityFromContext(context);
            // 获取 view 所在的 fragments
            Object fragment = AopUtils.getFragmentFromView(adapterView,activity);
            JSONObject properties = SensorsDataManager.buildPageInfo(activity);

            String idString = SensorsDataUtils.getViewId(adapterView);
            if (!TextUtils.isEmpty(idString)) {
                properties.put(SensorsDataConstants.ELEMENT_ID, idString);
            }
            properties.put(SensorsDataConstants.ELEMENT_POSITION, String.valueOf(position));

            if (adapterView instanceof Spinner) {
                properties.put(SensorsDataConstants.ELEMENT_TYPE, "Spinner");
                Object item = adapterView.getItemAtPosition(position);
                if (item != null) {
                    if (item instanceof String) {
                        properties.put(SensorsDataConstants.ELEMENT_CONTENT, item);
                    }
                }
            } else {
                if (adapterView instanceof ListView) {
                    properties.put(SensorsDataConstants.ELEMENT_TYPE, "ListView");
                } else if (adapterView instanceof GridView) {
                    properties.put(SensorsDataConstants.ELEMENT_TYPE, "GridView");
                }

                String viewText = null;
                if (view instanceof ViewGroup) {
                    try {
                        StringBuilder stringBuilder = new StringBuilder();
                        viewText = SensorsDataUtils.traverseViewContent(stringBuilder, (ViewGroup) view);
                        if (!TextUtils.isEmpty(viewText)) {
                            viewText = viewText.substring(0, viewText.length() - 1);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    viewText = SensorsDataUtils.getElementContent(view);
                }
                //$element_content
                if (!TextUtils.isEmpty(viewText)) {
                    properties.put(SensorsDataConstants.ELEMENT_CONTENT, viewText);
                }
                if (fragment != null) {
                    SensorsDataManager.getScreenNameAndTitleFromFragment(properties, fragment, activity);
                }
            }
            SensorsDataAPI.getInstance().track(SensorsDataConstants.APP_VIEW_CLICK__EVENT_NAME, properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * View 被点击，自动埋点
     *
     * @param view View
     * @return 是否通过用户协议，false表示没有通过协议，true表示通过用户协议
     */
    @Keep
    public static boolean trackViewOnClick(View view) {
        if (SensorsDataUtils.isDoubleClick(view)) {
            return false;
        }
        boolean isUserAgreement = true;
        try {
            SensorsDataAPI.OnUserAgreementListener mOnUserAgreement = SensorsDataAPI.getInstance().getListenerInfo().mOnUserAgreement;
            if (mOnUserAgreement != null) {
                isUserAgreement = mOnUserAgreement.onUserAgreement();
            }
            if (!isUserAgreement) {
                return false;
            }
            Activity activity = SensorsDataUtils.getActivityFromView(view);
            Object fragment = AopUtils.getFragmentFromView(view,activity);
            JSONObject jsonObject = SensorsDataManager.buildPageInfo(activity);
            if (fragment != null) {
                SensorsDataManager.getScreenNameAndTitleFromFragment(jsonObject, fragment, activity);
            }
            jsonObject.put(SensorsDataConstants.ELEMENT_TYPE, SensorsDataUtils.getElementType(view));
            jsonObject.put(SensorsDataConstants.ELEMENT_ID, SensorsDataUtils.getViewId(view));
            jsonObject.put(SensorsDataConstants.ELEMENT_CONTENT, SensorsDataUtils.getElementContent(view));
            SensorsDataAPI.getInstance().track(SensorsDataConstants.APP_VIEW_CLICK__EVENT_NAME, jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    @Keep
    public static void trackViewOnClickLambda(View view) {
        if (SensorsDataUtils.isDoubleClick(view)) {
            return;
        }
        boolean isUserAgreement = true;
        try {
            SensorsDataAPI.OnUserAgreementListener mOnUserAgreement = SensorsDataAPI.getInstance().getListenerInfo().mOnUserAgreement;
            if (mOnUserAgreement != null) {
                isUserAgreement = mOnUserAgreement.onUserAgreement();
            }
            if (!isUserAgreement) {
                return;
            }
            Activity activity = SensorsDataUtils.getActivityFromView(view);
            Object fragment = AopUtils.getFragmentFromView(view,activity);
            JSONObject jsonObject = SensorsDataManager.buildPageInfo(activity);
            if (fragment != null) {
                SensorsDataManager.getScreenNameAndTitleFromFragment(jsonObject, fragment, activity);
            }
            jsonObject.put(SensorsDataConstants.ELEMENT_TYPE, SensorsDataUtils.getElementType(view));
            jsonObject.put(SensorsDataConstants.ELEMENT_ID, SensorsDataUtils.getViewId(view));
            jsonObject.put(SensorsDataConstants.ELEMENT_CONTENT, SensorsDataUtils.getElementContent(view));
            SensorsDataAPI.getInstance().track(SensorsDataConstants.APP_VIEW_CLICK__EVENT_NAME, jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
