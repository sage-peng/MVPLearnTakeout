package com.xjd.mvplearntakeout.utils

import android.view.animation.*
import com.xjd.mvplearntakeout.ui.activity.BusinessActivity
import java.util.*

/**
 * Created by Administrator on 2018-11-10.
 */
object MyAnimationUtils {


    val DURATION: Long = 500
    open class animationlistner :Animation.AnimationListener {
        override fun onAnimationRepeat(animation: Animation?) {}

        override fun onAnimationEnd(animation: Animation?) {

        }

        override fun onAnimationStart(animation: Animation?) {}

    }


    fun showAnimation(): AnimationSet {
        val animationSet = AnimationSet(false)
        animationSet.duration = DURATION
        val alphaAnimation = AlphaAnimation(0.0f, 1.0f)
        alphaAnimation.duration = DURATION
        val rotateAnimation = RotateAnimation(0.0f, 720.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        rotateAnimation.duration = DURATION
        val translateAnimation = TranslateAnimation(Animation.RELATIVE_TO_SELF, 2f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f)
        translateAnimation.duration = DURATION

        animationSet.addAnimation(alphaAnimation)
        animationSet.addAnimation(rotateAnimation)
        animationSet.addAnimation(translateAnimation)
        return animationSet
    }

    fun hideAnimation(): AnimationSet {
        val animationSet = AnimationSet(false)
        animationSet.duration = DURATION
        val alphaAnimation = AlphaAnimation(1.0f, 0.0f)
        alphaAnimation.duration = DURATION
        val rotateAnimation = RotateAnimation(720.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        rotateAnimation.duration = DURATION
        val translateAnimation = TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 2f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f)
        translateAnimation.duration = DURATION

        animationSet.addAnimation(alphaAnimation)
        animationSet.addAnimation(rotateAnimation)
        animationSet.addAnimation(translateAnimation)
        return animationSet
    }


    fun parabolaAnimation(srcLocation:IntArray,desLocation:IntArray): AnimationSet {
        val animationSet = AnimationSet(false)
        animationSet.duration = DURATION

        val xTranslateAnimation = TranslateAnimation(Animation.ABSOLUTE, 0f,
                Animation.ABSOLUTE, desLocation[0].toFloat()-srcLocation[0].toFloat(),
                Animation.ABSOLUTE, 0f,
                Animation.ABSOLUTE, 0f)
        xTranslateAnimation.duration = DURATION

        val yTranslateAnimation = TranslateAnimation(Animation.ABSOLUTE, 0f,
                Animation.ABSOLUTE, 0f,
                Animation.ABSOLUTE, 0f,
                Animation.ABSOLUTE, desLocation[1].toFloat()-srcLocation[1].toFloat())
        yTranslateAnimation.setInterpolator(AccelerateInterpolator())
        yTranslateAnimation.duration = DURATION


        animationSet.addAnimation(xTranslateAnimation)
        animationSet.addAnimation(yTranslateAnimation)
        return animationSet
    }

}