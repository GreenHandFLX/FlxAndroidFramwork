package com.xinxi.ergatebesh;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.data.style.FontStyle;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest extends Activity {
    // smartTable 实例   注解方式
    private SmartTable table;
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.xinxi.ergatebesh", appContext.getPackageName());
    }
}