package me.xander.firstmod.entity.client.animation;

import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;

public class WhispererAnimations {
    public static final Animation IDLE = Animation.Builder.create(0.5f).looping()
        .addBoneAnimation("ring",
                new Transformation(Transformation.Targets.ROTATE,
                        new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                Transformation.Interpolations.LINEAR),
                        new Keyframe(0.25f, AnimationHelper.createRotationalVector(0f, -180f, 0f),
                                Transformation.Interpolations.LINEAR),
                        new Keyframe(0.5f, AnimationHelper.createRotationalVector(0f, -360f, 0f),
                                Transformation.Interpolations.LINEAR)))
        .addBoneAnimation("ring2",
                new Transformation(Transformation.Targets.ROTATE,
                        new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                Transformation.Interpolations.LINEAR),
                        new Keyframe(0.25f, AnimationHelper.createRotationalVector(0f, 180f, 0f),
                                Transformation.Interpolations.LINEAR),
                        new Keyframe(0.5f, AnimationHelper.createRotationalVector(0f, 360f, 0f),
                                Transformation.Interpolations.LINEAR)))
        .addBoneAnimation("ring3",
                new Transformation(Transformation.Targets.ROTATE,
                        new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                Transformation.Interpolations.LINEAR),
                        new Keyframe(0.25f, AnimationHelper.createRotationalVector(0f, -180f, 0f),
                                Transformation.Interpolations.LINEAR),
                        new Keyframe(0.5f, AnimationHelper.createRotationalVector(0f, -360f, 0f),
                                Transformation.Interpolations.LINEAR))).build();
    public static final Animation WALK = Animation.Builder.create(0.5f).looping()
            .addBoneAnimation("ring",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.25f, AnimationHelper.createRotationalVector(0f, -180f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.5f, AnimationHelper.createRotationalVector(0f, -360f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("ring2",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.25f, AnimationHelper.createRotationalVector(0f, 180f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.5f, AnimationHelper.createRotationalVector(0f, 360f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("ring3",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.25f, AnimationHelper.createRotationalVector(0f, -180f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.5f, AnimationHelper.createRotationalVector(0f, -360f, 0f),
                                    Transformation.Interpolations.LINEAR))).build();
}
