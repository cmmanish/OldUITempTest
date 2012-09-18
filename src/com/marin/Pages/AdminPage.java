
package com.marin.Pages;

import org.apache.log4j.Logger;

public class AdminPage extends PageWidgetHandler {

	static Logger log = Logger.getLogger(AdminPage.class);
	public static final String PAGE_TITTLE = "Marin: Activity Log";

	public static final String CLIENTS_SUBTAB =
		"//a[@id='setup_action_list_clients']";
	public static final String CLIENTS_CREATE_BUTTON =
		"//a[@id='setup_action_add_client']";
	public static final String CLIENT_SETTINGS_SAVE_BUTTON =
		"//input[@id='jsSaveButton']";

	public static final String CUSTOM_COLUMN_SUBTAB =
		"//a[@id='setup_action_custom_columns']";
	public static final String CUSTOM_COLUMN_CREATE_BUTTON =
		"//a[@id='custom_column_action_add']";
	public static final String CUSTOM_COLUMN_EDIT_BUTTON =
		"//a[@id='custom_column_action_edit']";
	public static final String CUSTOM_COLUMN_DELETE_BUTTON =
		"//a[@id='custom_column_action_delete']";
	public static final String CUSTOM_COLUMN_SETTING_BUBBLE_NAME_TF =
		"//input[@id='name']";
	public static final String CUSTOM_COLUMN_SETTING_BUBBLE_DEFINITION_TEXTAREA =
		"//textarea[@id='definition']";
	public static final String CUSTOM_COLUMN_SETTING_BUBBLE_FORMAT_SELECT =
		"//select[@id='formatType']";
	public static final String CUSTOM_COLUMN_SETTING_BUBBLE_SAVE_BUTTON =
		"//a[@id='conversion_type_submit']";
	public static final String CUSTOM_COLUMN_DELETE_CONFIRMATION_BUTTON =
		"//a[@id='conversion_type_submit']";

	public static final String ACTIVITY_LOG_SHOW_X_ROWS_DROPDOWN =
		"//select[@id='operation_table_row_select']";
	public static final String ACTIVITY_LOG_SUPER_SELECT =
		"id=id_gridCheckboxAll";
	public static final String ACTIVITY_LOG_CANCEL_BUTTON =
		"id=setup_action_cancel";
	public static final String ACTIVITY_LOG_HOLD_BUTTON =
		"//a[@id='setup_action_pausecartop']/img";
	public static final String ACTIVITY_LOG_POSTNOW_BUTTON =
		"//a[@id='setup_action_sendcartop']/img";
	public static final String ACTIVITY_LOG_CARTOP_COMMENT_TEXTAREA =
		"//textarea[@id='commentText']";

	public static String getActivityLogCartOPID(String rownumber) {

		return "//table[@id='left_table']/tbody/tr[" + rownumber + "]/td/a";
	}

	// postAllChangesToPublishers()

	// activityLogClearPendingChanges()
}
