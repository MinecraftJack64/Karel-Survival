package com.karel.game;
public interface ItemAccepter {
    void acceptItem(Item item);
    ItemHolder getHand();
}