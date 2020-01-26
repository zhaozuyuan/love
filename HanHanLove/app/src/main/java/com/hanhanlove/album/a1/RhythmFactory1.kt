package com.hanhanlove.album.a1

import com.hanhanlove.album.base.IRhythmFactory
import com.hanhanlove.util.AnimationUtil

class RhythmFactory1 : IRhythmFactory {
    override fun getAnimationList(): List<IRhythmFactory.AnimationBean> = listOf(
        IRhythmFactory.AnimationBean(1500, AnimationUtil.getBiggerAnim(1700)),
        IRhythmFactory.AnimationBean(800, AnimationUtil.getBiggerAnim(1600)),
        IRhythmFactory.AnimationBean(600, AnimationUtil.getBiggerAnim(1300)),
        IRhythmFactory.AnimationBean(500, null, false, true, 1),
        IRhythmFactory.AnimationBean(93, null, false, true, 0),
        IRhythmFactory.AnimationBean(93, null, false, true, 0),
        IRhythmFactory.AnimationBean(93, null, false, true, 0),
        IRhythmFactory.AnimationBean(93, null, false, true, 0),
        IRhythmFactory.AnimationBean(93, null, false, true, 0),
        IRhythmFactory.AnimationBean(93, null, false, true, 0),
        IRhythmFactory.AnimationBean(93, null, false, true, 0),
        IRhythmFactory.AnimationBean(93, null, false, true, 0),
        IRhythmFactory.AnimationBean(93, null, false, true, 0),
        IRhythmFactory.AnimationBean(93, null, false, true, 0),
        IRhythmFactory.AnimationBean(93, null, false, true, 0),
        IRhythmFactory.AnimationBean(93, null, false, true, 0),
        IRhythmFactory.AnimationBean(93, null, false, true, 0),
        IRhythmFactory.AnimationBean(93, null, false, true, 0),
        IRhythmFactory.AnimationBean(93, null, false, true, 0),
        IRhythmFactory.AnimationBean(93, null, false, true, 0),
        IRhythmFactory.AnimationBean(93, null, false, true, 0),
        IRhythmFactory.AnimationBean(93, null, false, true, 0),
        IRhythmFactory.AnimationBean(500, null, false, true, 2) ,
        IRhythmFactory.AnimationBean(1150, AnimationUtil.getBiggerAnim(2000)),
        IRhythmFactory.AnimationBean(750, AnimationUtil.getBiggerAnim(1400)),
        IRhythmFactory.AnimationBean(180, AnimationUtil.getBiggerAnim(1200)),
        IRhythmFactory.AnimationBean(180, AnimationUtil.getBiggerAnim(1200)),
        IRhythmFactory.AnimationBean(180, AnimationUtil.getBiggerAnim(1200)),
        IRhythmFactory.AnimationBean(180, AnimationUtil.getBiggerAnim(1200)),
        IRhythmFactory.AnimationBean(800, AnimationUtil.getBiggerAnim(1200)),
        IRhythmFactory.AnimationBean(180, AnimationUtil.getBiggerAnim(1200)),
        IRhythmFactory.AnimationBean(180, AnimationUtil.getBiggerAnim(1200)),
        IRhythmFactory.AnimationBean(180, AnimationUtil.getBiggerAnim(1200)),
        IRhythmFactory.AnimationBean(190, AnimationUtil.getBiggerAnim(1200)),
        IRhythmFactory.AnimationBean(820, AnimationUtil.getBiggerAnim(1600)),
        IRhythmFactory.AnimationBean(800, AnimationUtil.getBiggerAnim(1800)),
        IRhythmFactory.AnimationBean(790, AnimationUtil.getBiggerAnim(1800)),
        IRhythmFactory.AnimationBean(2300, AnimationUtil.getBiggerAnim(2400))
        )
}