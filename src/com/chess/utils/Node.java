package com.chess.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import chesspresso.move.Move;

public class Node {
  private String id;
  private short move;
  private final List<Node> children = new ArrayList<>();
  private final Node parent;
  private int occurrence;

  public Node(Node parent) {
    this.parent = parent;
    this.setOccurrence(1);
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public short getMove() {
    return move;
  }

  public void setMove(short move) {
    this.move = move;
  }

  public int getOccurrence() {
    return occurrence;
  }

  public void setOccurrence(int occurrence) {
    this.occurrence = occurrence;
  }

  public List<Node> getChildren() {
    return children;
  }

  public Node getParent() {
    return parent;
  }

  /**
   * Add moves to the tree
   * @param move
   * @return newly added move node
   */
  public Node addChild(short move) {
    
    // first check if the parent contains this move
    Node node = getChildren(getSiblings(this)).stream()
        .filter(x -> (move == x.getMove()))
        .findAny()
        .orElse(null);
    
    if(node != null){
      // TODO: have to increment the number of occurrence of the move
      // will need extra field for this
      node.setOccurrence(node.getOccurrence() + 1);
    } else{
      node = new Node(this);
      node.setId(Move.getString(move));
      node.setMove(move);
      this.getChildren().add(node);
    }
    return node;
  }

  public void addChildren(List<String> ids) {
    this.getChildren().addAll(ids.stream().map(id -> {
      Node node = new Node(this);
      node.setId(id);
      return node;
    }).collect(Collectors.toList()));
  }
  
  private List<Node> getSiblings(Node currentNode){
    Node parent = currentNode.getParent();
    // if the parent is null, then this is the root node
    return (parent != null) ? parent.getChildren() : Arrays.asList(currentNode);
  }
  
  private List<Node> getChildren(List<Node> nodes){
    List<Node> childNodes = new ArrayList<>();
    for(Node currentNode : nodes){
      childNodes.addAll(currentNode.getChildren());
    }
    return childNodes;
  }
}
