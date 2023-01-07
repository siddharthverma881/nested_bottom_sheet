package com.example.demo

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import com.example.demo.Constants.SheetType.Companion.SHEET_HOME
import com.example.demo.Constants.SheetType.Companion.SHEET_ONE
import com.example.demo.Constants.SheetType.Companion.SHEET_TWO
import com.google.android.material.bottomsheet.BottomSheetBehavior


class MainActivity : AppCompatActivity() {

    private lateinit var frameLayoutOne : FrameLayout
    private lateinit var frameLayoutTwo : FrameLayout
    private lateinit var mSheetBehaviorOne: BottomSheetBehavior<*>
    private lateinit var mSheetBehaviorTwo: BottomSheetBehavior<*>
    private lateinit var parentSheetOne : CoordinatorLayout
    private lateinit var parentSheetTwo : ConstraintLayout
    private lateinit var rlSheetOne : RelativeLayout
    private lateinit var rlSheetTwo : RelativeLayout
    private lateinit var llHomeOne : LinearLayout
    private lateinit var rlHomeTwo : RelativeLayout
    private lateinit var tvEmi : TextView
    private lateinit var tvBankAccount : TextView
    private lateinit var rlOneCollapsed : RelativeLayout
    private lateinit var llOneExpanded : LinearLayout
    private lateinit var llTwoExpanded : LinearLayout
    private lateinit var llHomeParent : RelativeLayout
    private var sheetVisible = SHEET_HOME
    private var lastBackPressed = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()

        llHomeParent.setOnClickListener{
            sheetVisible = SHEET_HOME
            mSheetBehaviorOne.state = BottomSheetBehavior.STATE_COLLAPSED
            mSheetBehaviorTwo.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        mSheetBehaviorOne = BottomSheetBehavior.from(frameLayoutOne)
        parentSheetOne.setOnClickListener{
            sheetVisible = SHEET_ONE
            Utils.setVisibility(View.GONE, tvEmi, llHomeOne)
            Utils.setVisibility(View.VISIBLE, rlHomeTwo, llOneExpanded)
            mSheetBehaviorOne.state = BottomSheetBehavior.STATE_EXPANDED
        }
        mSheetBehaviorOne.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback(){
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when(newState){
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        Utils.setVisibility(View.GONE, tvEmi)
                        Utils.setVisibility(View.VISIBLE, rlSheetOne, llOneExpanded)
                        rlSheetOne.background = ContextCompat.getDrawable(this@MainActivity, R.drawable.bg_sheet_expanded)
                        mSheetBehaviorOne.setPeekHeight(Utils.dpToPx(this@MainActivity,600F))
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        Utils.setVisibility(View.GONE, rlSheetOne, rlHomeTwo, llOneExpanded)
                        Utils.setVisibility(View.VISIBLE, tvEmi, llHomeOne)
                        mSheetBehaviorOne.setPeekHeight(Utils.dpToPx(this@MainActivity,60F))
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> { }
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> { }
                    BottomSheetBehavior.STATE_HIDDEN -> { }
                    BottomSheetBehavior.STATE_SETTLING -> { }
                }
            }
            override fun onSlide(bottomSheet: View, slideOffset: Float) { }
        })

        mSheetBehaviorTwo = BottomSheetBehavior.from(frameLayoutTwo)

        parentSheetTwo.setOnClickListener {
            sheetVisible = SHEET_TWO
            Utils.setVisibility(View.GONE, tvBankAccount, llOneExpanded)
            Utils.setVisibility(View.VISIBLE, rlOneCollapsed)
            mSheetBehaviorTwo.state = BottomSheetBehavior.STATE_EXPANDED
            rlSheetOne.background = ContextCompat.getDrawable(this, R.drawable.bg_sheet_expanded)
        }
        rlSheetOne.setOnClickListener{
            sheetVisible = SHEET_ONE
            rlOneCollapsed.visibility = View.GONE
            llOneExpanded.visibility = View.VISIBLE
            mSheetBehaviorTwo.state = BottomSheetBehavior.STATE_COLLAPSED
            rlSheetTwo.background = ContextCompat.getDrawable(this, R.drawable.bg_sheet_collapsed)
            rlSheetOne.background = ContextCompat.getDrawable(this, R.drawable.bg_sheet_expanded)
        }

        rlSheetTwo.setOnClickListener(View.OnClickListener { return@OnClickListener })

        mSheetBehaviorTwo.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when(newState){
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        Utils.setVisibility(View.GONE, tvBankAccount, llOneExpanded)
                        Utils.setVisibility(View.VISIBLE, rlSheetTwo, rlOneCollapsed, llTwoExpanded)
                        rlSheetTwo.background = ContextCompat.getDrawable(this@MainActivity, R.drawable.bg_sheet_expanded)
                        rlSheetOne.background = ContextCompat.getDrawable(this@MainActivity,R.drawable.bg_sheet_background)
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        Utils.setVisibility(View.GONE, rlSheetTwo, rlOneCollapsed, llTwoExpanded)
                        Utils.setVisibility(View.VISIBLE, tvBankAccount, llOneExpanded)
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> { }
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> { }
                    BottomSheetBehavior.STATE_HIDDEN -> { }
                    BottomSheetBehavior.STATE_SETTLING -> { }
                }
            }
            override fun onSlide(bottomSheet: View, slideOffset: Float) { }
        })
    }

    override fun onBackPressed() {
        when(sheetVisible){
            SHEET_HOME -> {
                val currentTimeStamp = System.currentTimeMillis()
                val difference: Long = currentTimeStamp - lastBackPressed
                if (difference > 2000) {
                    Toast.makeText(this, "Tap again to exit",Toast.LENGTH_SHORT).show()
                    lastBackPressed = currentTimeStamp
                } else {
                    super.onBackPressed()
                }
            }
            SHEET_ONE-> {
                sheetVisible = SHEET_HOME
                mSheetBehaviorOne.state = BottomSheetBehavior.STATE_COLLAPSED
                mSheetBehaviorTwo.state = BottomSheetBehavior.STATE_COLLAPSED
            }
            SHEET_TWO -> {
                sheetVisible = SHEET_ONE
                rlOneCollapsed.visibility = View.GONE
                llOneExpanded.visibility = View.VISIBLE
                mSheetBehaviorTwo.state = BottomSheetBehavior.STATE_COLLAPSED
                rlSheetTwo.background = ContextCompat.getDrawable(this, R.drawable.bg_sheet_collapsed)
                rlSheetOne.background = ContextCompat.getDrawable(this, R.drawable.bg_sheet_expanded)
            }
        }
    }

    private fun init(){
        llHomeParent = findViewById(R.id.view_1)
        llHomeOne = findViewById(R.id.llHomeOne)
        llOneExpanded = findViewById(R.id.llOneExpanded)
        llTwoExpanded = findViewById(R.id.llTwoExpanded)
        rlOneCollapsed = findViewById(R.id.rlOneCollapsed)
        rlHomeTwo = findViewById(R.id.rlHomeTwo)
        tvEmi = findViewById(R.id.tvEmi)
        tvBankAccount = findViewById(R.id.tvBankAccount)
        rlSheetOne = findViewById(R.id.rlSheetOne)
        rlSheetTwo = findViewById(R.id.rlSheetTwo)
        frameLayoutOne = findViewById(R.id.frame_sheet_one)
        parentSheetOne = findViewById(R.id.constraintRoot)
        frameLayoutTwo = findViewById(R.id.frame_sheet_two)
        parentSheetTwo = findViewById(R.id.parent_two)
    }
}