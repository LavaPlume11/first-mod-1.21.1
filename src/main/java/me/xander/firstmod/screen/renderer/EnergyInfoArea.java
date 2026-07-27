package me.xander.firstmod.screen.renderer;import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.Rect2i;
import net.minecraft.text.Text;


import java.util.List;

/*
 *  BluSunrize
 *  Copyright (c) 2021
 *
 *  This code is licensed under "Blu's License of Common Sense" (FORGE VERSION)
 *  Modified for Fabric by: Kaupenjoe
 */
public class EnergyInfoArea {
    private final Rect2i area;
    private final int energy;
    private final int energyMax;

    public EnergyInfoArea(int xMin, int yMin)  {
        this(xMin, yMin, 0, 0,8,64);
    }

    public EnergyInfoArea(int xMin, int yMin, int energy, int energyMax)  {
        this(xMin, yMin, energy,energyMax,8,64);
    }

    public EnergyInfoArea(int xMin, int yMin, int energy, int energyMax, int width, int height)  {
        area = new Rect2i(xMin, yMin, width, height);
        this.energy = energy;
        this.energyMax = energyMax;
    }

    public List<Text> getTooltips() {
        return List.of(Text.literal(energy+" / "+energyMax+" E"));
    }

    public void draw(DrawContext context) {
        final int height = area.getHeight();
        int stored = (int)(height*(energy/(float)energyMax));
        context.fillGradient(
                area.getX(), area.getY()+(height-stored),
                area.getX() + area.getWidth(), area.getY() +area.getHeight(),
                0xffb51500, 0xff600b00
        );
    }
}
