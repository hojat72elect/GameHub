package ca.six.hojat.gamehub.common.extensions

import ca.six.hojat.gamehub.R


enum class WindowAnimations(
    val id: Int,
    val windowAEnterAnimation: Int = 0,
    val windowAExitAnimation: Int = 0,
    val windowBEnterAnimation: Int = 0,
    val windowBExitAnimation: Int = 0
) {

    DEFAULT_ANIMATIONS(
        id = 1
    ),

    NO_ANIMATIONS(
        id = 2,
        windowAEnterAnimation = R.anim.no_window_animation,
        windowAExitAnimation = R.anim.no_window_animation,
        windowBEnterAnimation = R.anim.no_window_animation,
        windowBExitAnimation = R.anim.no_window_animation
    ),

    FADING_ANIMATIONS(
        id = 3,
        windowAEnterAnimation = R.anim.fade_in_animation,
        windowAExitAnimation = R.anim.fade_out_animation,
        windowBEnterAnimation = R.anim.fade_in_animation,
        windowBExitAnimation = R.anim.fade_out_animation
    ),

    KITKAT_SCALING_ANIMATIONS(
        id = 4,
        windowAEnterAnimation = R.anim.kitkat_scaling_window_a_enter_animation,
        windowAExitAnimation = R.anim.kitkat_scaling_window_a_exit_animation,
        windowBEnterAnimation = R.anim.kitkat_scaling_window_b_enter_animation,
        windowBExitAnimation = R.anim.kitkat_scaling_window_b_exit_animation
    ),

    HORIZONTAL_SLIDING_ANIMATIONS(
        id = 5,
        windowAEnterAnimation = R.anim.horizontal_sliding_window_a_enter_animation,
        windowAExitAnimation = R.anim.horizontal_sliding_window_a_exit_animation,
        windowBEnterAnimation = R.anim.horizontal_sliding_window_b_enter_animation,
        windowBExitAnimation = R.anim.horizontal_sliding_window_b_exit_animation
    ),

    VERTICAL_SLIDING_ANIMATIONS(
        id = 6,
        windowAEnterAnimation = R.anim.vertical_sliding_window_a_enter_animation,
        windowAExitAnimation = R.anim.vertical_sliding_window_a_exit_animation,
        windowBEnterAnimation = R.anim.vertical_sliding_window_b_enter_animation,
        windowBExitAnimation = R.anim.vertical_sliding_window_b_exit_animation
    ),

    OVERSHOOT_SCALING_ANIMATIONS(
        id = 7,
        windowAEnterAnimation = R.anim.overshoot_scaling_window_a_enter_animation,
        windowAExitAnimation = R.anim.overshoot_scaling_window_a_exit_animation,
        windowBEnterAnimation = R.anim.overshoot_scaling_window_b_enter_animation,
        windowBExitAnimation = R.anim.overshoot_scaling_window_b_exit_animation
    );
}
