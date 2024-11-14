package dev.sebastianb.client.buttons;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BaseButton
{
    private String menuNavigation;
    private int xAxis;
    private int yAxis;
    private Texture texture;
    private float scaleMultiplier;

    public BaseButton (String menuNavigation, int x, int y, String assetLocation,float scaleMultiplier)
    {
        this.menuNavigation = menuNavigation;
        this.xAxis = x;
        this.yAxis = y;
        this.scaleMultiplier  = scaleMultiplier;
        this.texture = new Texture(assetLocation);
    }

    public  int[] getTextureCoordinate()
    {

        return new int[] {0, 0, texture.getWidth(), texture.getHeight()};
    }


}
