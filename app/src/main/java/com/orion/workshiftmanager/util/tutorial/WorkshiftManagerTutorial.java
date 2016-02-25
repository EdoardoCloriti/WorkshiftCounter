package com.orion.workshiftmanager.util.tutorial;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.orion.workshiftmanager.R;
import com.orion.workshiftmanager.util.Property;
import com.orion.workshiftmanager.util.db.AccessToDB;

/**
 * Created by Edoardo on 27/01/2016.
 */
public class WorkshiftManagerTutorial {

    public static final String WORK_SHIFT_MANAGER = "WorkShiftManager";
    public static final String WORK_SHFIT_MANAGER_SETTING = "WorkShfitManagerSetting";
    public static final String MULTI_SELECTION_MENU = "MultiSelectionMenu";
    public static final String MANAGE_WORKSHIFT = "ManageWorkshift";
    public static final String ADD_OVERTIME = "AddOvertime";
    public static final String STARLING_HOURS = "StarlingHours";

    public static boolean isTutorialReq(Context context, String className) {
        AccessToDB db = new AccessToDB();
        if (db.getProperty(Property.TUTORIAL + context.getClassLoader(), context) == null)
            return false;
        else
            return false;
    }

    public static void showWorkShiftManagerTurorial(Context context, String tutorialName) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.tutorial_title));
        builder.setMessage(context.getString(getMessageId(context, tutorialName)));
        builder.setPositiveButton(context.getString(R.string.msg_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.create();
        builder.show();

    }

    private static int getMessageId(Context context, String tutorialName) {

        if (WORK_SHIFT_MANAGER.equals(tutorialName))
            return R.string.tutorial_WorkShfitManager;
        else if (WORK_SHFIT_MANAGER_SETTING.equals(tutorialName))
            return R.string.tutorial_WorkShfitManager_setting;
        else if (MULTI_SELECTION_MENU.equals(tutorialName))
            return R.string.tutorial_MultiSelectionMenu;
        else if (MANAGE_WORKSHIFT.equals(tutorialName))
            return R.string.tutorial_ManageWorkshift;
        else if (ADD_OVERTIME.equals(tutorialName))
            return R.string.tutorial_AddOvertime;
        else if (STARLING_HOURS.equals(tutorialName))
            return R.string.tutorial_StarlingHour;
        else
            return 0;

    }
}
