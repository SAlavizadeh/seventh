/*
 * see license.txt 
 */
package seventh.ui.view;

import seventh.client.gfx.Camera;
import seventh.client.gfx.Canvas;
import seventh.client.gfx.Colors;
import seventh.client.gfx.Renderable;
import seventh.math.Rectangle;
import seventh.shared.TimeStep;
import seventh.ui.Button;
import seventh.ui.ScrollBar;

/**
 * @author Tony
 *
 */
public class ScrollBarView implements Renderable {

    private ScrollBar scrollBar;
    
    /**
     * @param scrollBar
     */
    public ScrollBarView(ScrollBar scrollBar) {
        this.scrollBar = scrollBar;        
    }

    @Override
    public void update(TimeStep timeStep) {
    }

    private void renderButton(Canvas canvas, Button btn) {
        
        Rectangle bounds = btn.getScreenBounds();
        canvas.fillRect(bounds.x, bounds.y, bounds.width, bounds.height, 0xff383e18);
        
        int a = 240;
        if(btn.isHovering()) {
            a = 100;
            canvas.drawRect(bounds.x, bounds.y, bounds.width, bounds.height, scrollBar.getForegroundColor());
        }
        
        canvas.fillRect(bounds.x, bounds.y, bounds.width, bounds.height, Colors.setAlpha(0xff282c0c, a));
        canvas.drawRect(bounds.x, bounds.y, bounds.width, bounds.height, 0xff000000);
        
        if(btn.isHovering()) {         
            canvas.drawRect(bounds.x, bounds.y, bounds.width, bounds.height, Colors.setAlpha(scrollBar.getForegroundColor(), 180));
        }
        
        final int stripColor = scrollBar.getForegroundColor();
        switch(this.scrollBar.getOrientation()) {
            case Horizontal: {
                break;
            }
            case Vertical: {
                int width = bounds.height;
                int stichesWidth = 21;
                int y = bounds.y + (width / 2) + (stichesWidth / 5);
                int stichHeight = bounds.width - 5;
                
                //y += stichesWidth/5;
                canvas.drawLine(bounds.x + (stichHeight / 2) , y, bounds.x + (bounds.width - (stichHeight / 2)), y, stripColor);
                break;
            }
        }
    }
    
    private void renderHandle(Canvas canvas, Button btn) {
        renderButton(canvas, btn);
        Rectangle bounds = btn.getScreenBounds();
        
        final int stripColor = scrollBar.getForegroundColor();
        switch(this.scrollBar.getOrientation()) {
            case Horizontal: {
                int width = bounds.width;
                int stichesWidth = 21;
                int x = bounds.x + (width / 2) - stichesWidth;
                int stichHeight = bounds.height / 3;
                
                canvas.drawLine(x, bounds.y + (bounds.height - stichHeight), x, bounds.y + stichHeight, stripColor);
                x += stichesWidth/3;
                canvas.drawLine(x, bounds.y + (bounds.height - stichHeight), x, bounds.y + stichHeight, stripColor);
                x += stichesWidth/3;
                canvas.drawLine(x, bounds.y + (bounds.height - stichHeight), x, bounds.y + stichHeight, stripColor);
                break;
            }
            case Vertical: {
                int width = bounds.height;
                int stichesWidth = 21;
                int y = bounds.y + (width / 2) + (stichesWidth / 5);
                int stichHeight = bounds.width - 5;
                
                canvas.drawLine(bounds.x + (stichHeight / 2) , y, bounds.x + (bounds.width - (stichHeight / 2)), y, stripColor);
                y += stichesWidth/5;
                canvas.drawLine(bounds.x + (stichHeight / 2) , y, bounds.x + (bounds.width - (stichHeight / 2)), y, stripColor);
                y += stichesWidth/5;
                canvas.drawLine(bounds.x + (stichHeight / 2) , y, bounds.x + (bounds.width - (stichHeight / 2)), y, stripColor);                
                break;
            }
        }
    }
    
    @Override
    public void render(Canvas canvas, Camera camera, float alpha) {
        Rectangle bounds = this.scrollBar.getScreenBounds();
        canvas.fillRect(bounds.x, bounds.y, bounds.width, bounds.height, this.scrollBar.getBackgroundColor());

        canvas.drawRect(bounds.x, bounds.y, bounds.width, bounds.height, 0xff000000);
        
        renderButton(canvas, this.scrollBar.getTopButton());
        renderButton(canvas, this.scrollBar.getBottomButton());
        renderHandle(canvas, this.scrollBar.getHandleButton());
        
    }

}
