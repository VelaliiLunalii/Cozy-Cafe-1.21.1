package io.github.velaliilunalii.cozy_cafe.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

public class MixingParticle extends WaterDropParticle {
    MixingParticle(ClientLevel p_107929_, double p_107930_, double p_107931_, double p_107932_, double p_107933_, double p_107934_, double p_107935_) {
        super(p_107929_, p_107930_, p_107931_, p_107932_);
        this.gravity = 0.04F;
        if (p_107934_ == (double)0.0F && (p_107933_ != (double)0.0F || p_107935_ != (double)0.0F)) {
            this.xd = p_107933_;
            this.yd = 0.1;
            this.zd = p_107935_;
        }

    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public Provider(SpriteSet sprites) {
            this.sprite = sprites;
        }

        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            MixingParticle mixingParticle = new MixingParticle(level, x, y, z, xSpeed, ySpeed, zSpeed);
            mixingParticle.pickSprite(this.sprite);
            return mixingParticle;
        }
    }
}
