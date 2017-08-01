package abr.mod.photoptics.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class RenderHelper {
	
    public static void drawTexturedRect(double posX, double posY, double width, double height)
    {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder worldRenderer = tessellator.getBuffer();
        worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos((double)(posX + 0), (double)(posY + height), 0.0).tex(0.0, 1.0).endVertex();
        worldRenderer.pos((double)(posX + width), (double)(posY + height), 0.0).tex(1.0, 1.0).endVertex();
        worldRenderer.pos((double)(posX + width), (double)(posY + 0), 0.0).tex(1.0, 0.0).endVertex();
        worldRenderer.pos((double)(posX + 0), (double)(posY + 0), 0.0).tex(0.0, 0.0).endVertex();
        tessellator.draw();
    }

}
